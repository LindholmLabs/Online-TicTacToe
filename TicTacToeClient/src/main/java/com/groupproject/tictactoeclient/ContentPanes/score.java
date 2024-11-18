package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import javax.swing.*;
import java.awt.*;

/**
 * Class to display the score panel with player stats.
 */
public class score extends CustomPanel {
    public score(TicTacToeClient client) {
//super(); // Call the parent constructor

        // Get the logged-in UID from the client
        String loggedInUID = client.UID; 
        System.out.println("Logged-in UID: " + loggedInUID);

        // Panel to display the stats
        setLayout(new BorderLayout());
        JLabel statsLabel = new JLabel();
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statsLabel.setForeground(Color.BLACK);
        add(statsLabel, BorderLayout.CENTER);

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
                    String gameState =  columns[3].trim();  // Game state as a string

                    // Convert game state to an integer
                    int gameStateInt = Integer.parseInt(gameState);

                    // Determine results for the logged-in player
                    
                        // Logged-in player is Player 1
                        
                        if (gameStateInt == 1) { // Player 1 wins
                            wins++;
                            System.out.println("Win is updated");
                        } else if (gameStateInt == 2) { // Player 2 wins
                            losses++;
                            System.out.println("loss is updated");
                        }
                    
                       
                    
                } catch (Exception rowException) {
                    System.err.println("Error processing row: " + game);
                    rowException.printStackTrace();
                }
            }

            // Display the result as a summary
            statsLabel.setText(String.format("Player UID %s: Wins = %d, Losses = %d", loggedInUID, wins, losses));
        } catch (Exception e) {
            // Catch any unexpected errors
            statsLabel.setText("An error occurred while calculating player stats.");
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated method stub
    }
}
