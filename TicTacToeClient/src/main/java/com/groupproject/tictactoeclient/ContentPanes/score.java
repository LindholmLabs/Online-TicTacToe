package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class score extends CustomPanel {
    public score(TicTacToeClient client) {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // Use JTextArea for multiline output
        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        statsArea.setBackground(getBackground());
        statsArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(statsArea);
        add(scrollPane);

        // Back Button
        JButton backButton = new JButton("Back");
        add(backButton);

        // Layout constraints for JTextArea
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -50, SpringLayout.SOUTH, this);

        // Layout constraints for Back Button
        layout.putConstraint(SpringLayout.SOUTH, backButton, -10, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, backButton, 10, SpringLayout.WEST, this);

        // Back Button Action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.showPanel(new MainContentPanel(client));
            }
        });

        try {
            String leagueData = client.proxy.leagueTable(); //stores our leagueTable data into leagueData variable
            System.out.println("League Data: " + leagueData);

            // Check if the response indicates no games or a database error
            if (leagueData.equals("ERROR-NOGAMES")) {
                statsArea.setText("No games found.");
                return;
            } else if (leagueData.equals("ERROR-DB")) {
                statsArea.setText("Database error occurred.");
                return;
            }

            // Prepare header for stats
            StringBuilder statsBuilder = new StringBuilder();
            statsBuilder.append(String.format("%-20s %-10s %-10s %-10s\n", "Username", "Wins", "Losses", "Draws")); //headers for the user, wins, losses and draws
            statsBuilder.append("-".repeat(50)).append("\n");

            // Process league data
            String[] games = leagueData.split("\n"); // splits the league data into its diffrent games
            //counters for the wins, losses and draws
            int wins = 0; 
            int losses = 0;
            int draws = 0;

            for (String game : games) {
                try {
                    String[] columns = game.split(","); //splits each game into player1s ID, Player2s ID and the game state
                    if (columns.length < 4) {
                        throw new IllegalArgumentException("Malformed row: " + game);
                    }
                    //split game stores the users ID and the game state
                    String player1UID = columns[1].trim();
                    String player2UID = columns[2].trim();
                    String gameState = columns[3].trim();
                    int gameStateInt = Integer.parseInt(gameState); //turns game state into an int for if statments
                    //checks the game state and assigns a win, loss or draw for a user
                    if (player1UID.equals(client.username)) {
                        if (gameStateInt == 1) { // Player 1 wins
                            wins++;
                        } else if (gameStateInt == 2) { // Player 2 wins
                            losses++;
                        } else if (gameStateInt == 3) { // Draw
                            draws++;
                        }
                    } else if (player2UID.equals(client.username)) {
                        if (gameStateInt == 2) { // Player 2 wins
                            wins++;
                        } else if (gameStateInt == 1) { // Player 1 wins
                            losses++;
                        } else if (gameStateInt == 3) { // Draw
                            draws++;
                        }
                    }

                } catch (Exception rowException) {
                    System.err.println("Error processing row: " + game);
                    rowException.printStackTrace();
                }
            }

            // Add the user's stats to the output
            statsBuilder.append(String.format("%-20s %-10d %-10d %-10d\n", client.username, wins, losses, draws)); //prints out all the wins, losses and draws for a user

            // Set the stats to the JTextArea
            statsArea.setText(statsBuilder.toString());

        } catch (Exception e) {
            statsArea.setText("An error occurred while calculating player stats.");
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {
        // Not supported yet
    }
}
