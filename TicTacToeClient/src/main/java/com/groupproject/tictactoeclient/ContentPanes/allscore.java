package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


public class allscore extends CustomPanel {
    public allscore(TicTacToeClient client) {
        
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // Text Area for displaying scores
        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Add Text Area inside a Scroll Pane
        JScrollPane scrollPane = new JScrollPane(statsArea);
        add(scrollPane);

        // Back Button
        JButton backButton = new JButton("Back");
        add(backButton);

        // Set constraints for Scroll Pane
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -50, SpringLayout.SOUTH, this);

        // Set constraints for Back Button
        layout.putConstraint(SpringLayout.SOUTH, backButton, -10, SpringLayout.SOUTH, this); 
        layout.putConstraint(SpringLayout.WEST, backButton, 10, SpringLayout.WEST, this);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello, going back!");
                client.showPanel(new MainContentPanel(client));
            }
        });

        // Populate stats area
        try {
            String leagueData = client.proxy.leagueTable();
            if (leagueData.equals("ERROR-NOGAMES")) {
                statsArea.setText("No games found.");
                return;
            } else if (leagueData.equals("ERROR-DB")) {
                statsArea.setText("Database error occurred.");
                return;
            }

            // Split the result into rows
            String[] games = leagueData.split("\n"); //splits the leagueTable into its games 
            Map<String, int[]> playerStats = new HashMap<>(); //The variable playerStats is of type Map<String, int[]>

           
            for (String game : games) {
                try {
                    String[] columns = game.split(","); //splits the games into the users ID and the game state
                    if (columns.length < 4) {
                        throw new IllegalArgumentException("Malformed row: " + game);
                    }

                    String player1UID = columns[1].trim(); // Player 1 UID
                    String player2UID = columns[2].trim(); // Player 2 UID
                    String gameState = columns[3].trim();  // Game state as a string

                    int gameStateInt = Integer.parseInt(gameState); //turns game state into an int for if statments

                    // update the stats for Player one
                    playerStats.putIfAbsent(player1UID, new int[3]);
                    playerStats.putIfAbsent(player2UID, new int[3]);

                    if (gameStateInt == 1) { // player 1 wins
                        playerStats.get(player1UID)[0]++; // increment wins for Player 1
                        playerStats.get(player2UID)[1]++; // increment losses for Player 2
                    } else if (gameStateInt == 2) { // player 2 wins
                        playerStats.get(player2UID)[0]++; // increment wins for Player 2
                        playerStats.get(player1UID)[1]++; // increment losses for Player 1
                    } else if (gameStateInt == 3) { // player 2 wins
                        playerStats.get(player2UID)[2]++; //draw
                        playerStats.get(player1UID)[2]++; //draw
                    }

                } catch (Exception rowException) {
                    System.err.println("Error processing row: " + game);
                    rowException.printStackTrace();
                }
            }

            // Build the display string
            StringBuilder statsBuilder = new StringBuilder();
//            statsBuilder.append(String.format("%-20s %-10s %-10s %-10s\n", "Username", "Wins", "Losses", "Draws"));//headers for cleaner UI
            statsBuilder.append("-".repeat(50)).append("\n");

            for (Map.Entry<String, int[]> entry : playerStats.entrySet()) {
                String username = entry.getKey(); //updates the wins, losses and draws for this specific user
                int wins = entry.getValue()[0]; 
                int losses = entry.getValue()[1];
                int draws = entry.getValue()[2];
                statsBuilder.append(String.format("%-20s %-10d %-10d %-10d\n", username, wins, losses, draws)); //prints out all the wins losses and draws along with the username
            }
            // Set the stats to the JTextArea
            statsArea.setText(statsBuilder.toString());

        } catch (Exception e) {
            statsArea.setText("An error occurred while calculating player stats.");
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
