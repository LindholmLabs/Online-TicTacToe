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

        
        JLabel statsLabel = new JLabel();
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsLabel.setForeground(Color.BLACK);
        add(statsLabel);

        // Back Button
        JButton backButton = new JButton("Back");
        add(backButton);

        
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, statsLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, statsLabel, 20, SpringLayout.NORTH, this);

        
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
            
            String leagueData = client.proxy.leagueTable();
            System.out.println("League Data: " + leagueData);

            // Check if the response indicates no games or a database error
            if (leagueData.equals("ERROR-NOGAMES")) {
                statsLabel.setText("No games found.");
                return;
            } else if (leagueData.equals("ERROR-DB")) {
                statsLabel.setText("Database error occurred.");
                return;
            }

            
            String[] games = leagueData.split("\n");
            int wins = 0;
            int losses = 0;

            
            for (String game : games) {
                try {
                    String[] columns = game.split(","); 
                    if (columns.length < 4) {
                        throw new IllegalArgumentException("Malformed row: " + game);
                    }

                    String player1UID = columns[1].trim(); // Player 1 UID
                    String player2UID = columns[2].trim(); // Player 2 UID
                    String gameState = columns[3].trim();  // Game state (change to a int later)

                    int gameStateInt = Integer.parseInt(gameState);

                    if (player1UID.equals(client.username)) {
                        if (gameStateInt == 1) { // player 1 wins and adds to the score
                            wins++;
                        } else if (gameStateInt == 2) { // player 2 wins and adds to the score
                            losses++;
                        }
                    } else if (player2UID.equals(client.username)) {
                        if (gameStateInt == 2) { // P2 wins
                            wins++;
                        } else if (gameStateInt == 1) { // P1 wins
                            losses++;
                        }
                    }

                } catch (Exception rowException) {
                    System.err.println("Error processing row: " + game);
                    rowException.printStackTrace();
                }
            }

            // Display the result as a summary
            statsLabel.setText(String.format("Player %s: Wins = %d, Losses = %d", client.username, wins, losses));
        } catch (Exception e) {
            statsLabel.setText("An error occurred while calculating player stats.");
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated method stub
    }
}
