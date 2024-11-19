package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to display the score panel with player stats.
 */
public class score extends CustomPanel {
    public score(TicTacToeClient client) {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // Label to display stats
        JLabel statsLabel = new JLabel();
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsLabel.setForeground(Color.BLACK);
        add(statsLabel);

        // Back Button
        JButton backButton = new JButton("Back");
        add(backButton);

        // Position the statsLabel (centered horizontally, at the top)
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, statsLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, statsLabel, 20, SpringLayout.NORTH, this);

        // Position the backButton (bottom-left corner)
        layout.putConstraint(SpringLayout.SOUTH, backButton, -10, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, backButton, 10, SpringLayout.WEST, this);

        try {
            // Call the leagueTable method to get all games
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

            // Split the result into rows
            String[] games = leagueData.split("\n");
            int wins = 0;
            int losses = 0;

            // Parse each game's data
            for (String game : games) {
                try {
                    String[] columns = game.split(","); // Assuming data is comma-separated
                    if (columns.length < 4) {
                        throw new IllegalArgumentException("Malformed row: " + game);
                    }

                    String player1UID = columns[1].trim(); // Player 1 UID
                    String player2UID = columns[2].trim(); // Player 2 UID
                    String gameState = columns[3].trim();  // Game state as a string

                    int gameStateInt = Integer.parseInt(gameState);

                    if (player1UID.equals(client.username)) {
                        if (gameStateInt == 1) { // Player 1 wins
                            wins++;
                        } else if (gameStateInt == 2) { // Player 2 wins
                            losses++;
                        }
                    } else if (player2UID.equals(client.username)) {
                        if (gameStateInt == 2) { // Player 2 wins
                            wins++;
                        } else if (gameStateInt == 1) { // Player 1 wins
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

        // Back Button Action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.showPanel(new MainContentPanel(client));
            }
        });
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated method stub
    }
}
