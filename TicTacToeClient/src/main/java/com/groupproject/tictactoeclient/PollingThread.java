/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient;

import com.groupproject.tictactoeclient.ContentPanes.MainContentPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author William
 */
public class PollingThread implements Runnable {

    private TicTacToeClient client;
    private HelperMethods helperMethods;

    //flag used to keep track if game is over so the dialog box doesnt keep appearing
    private boolean gameOver = false;

    public PollingThread(TicTacToeClient client) {
        this.client = client;
        helperMethods = new HelperMethods(client);
    }

    /**
     * TicTacToe polling thread.
     * Calls on TicTacToeClient to update the currently open CustomPanel, extending JPanel
     * Also used to periodically fetch new data from server (once per second)
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);

                if (client.UID == null || client.UID.isEmpty() || Integer.parseInt(client.UID) == -1) {
                    continue;
                }

                client.BOARD = helperMethods.GetBoard();

                client.openGames = client.proxy.showOpenGames();
                client.refreshCurrentPanel();
                System.out.println("open games panel resetting\n");

                // if there is no game id, dont run code below
                if (client.GID.isBlank() || client.GID.isEmpty()) {
                    continue;
                }

                // if there are no moves, dont run code below
                if (client.NUM_OF_MOVES < 2) {
                    continue;
                }

                //call the web server to check if the game has been won or not yet using the WS checkWin function
                String result = client.proxy.checkWin(Integer.parseInt(client.GID));
                System.out.println("checkWin: " + result);
                //if the game is not over check the result of the game
                switch (result) {

                    case "1":
                        // Player 1 wins  
                        //Show on both player's frames player 1 has won
                        JOptionPane.showMessageDialog(client.frame, "Player 1 wins!");
                        //Set the gamestate to 1 (player 1 has won)
                        client.proxy.setGameState(Integer.parseInt(client.GID), 1);
                        //Show opions - main menu or quit 
                        helperMethods.showOptions();
                        gameOver = true;
                        break;
                    case "2":
                        // Player 2 wins, show on both player's frame player 2 has won
                        JOptionPane.showMessageDialog(client.frame, "Player 2 wins!");
                        //Set the game state to 2 indicating player 2 has won the game
                        client.proxy.setGameState(Integer.parseInt(client.GID), 2);
                        //Show options - main menu or quit
                        helperMethods.showOptions();
                        gameOver = true;
                        break;
                    case "3":
                        // It's a draw, show on both players screen its a draw
                        JOptionPane.showMessageDialog(client.frame, "It's a draw!");
                        //Set the gamestate to 3 indicating game is a draw
                        client.proxy.setGameState(Integer.parseInt(client.GID), 3);
                        //Show options - main menu or quit
                        helperMethods.showOptions();
                        gameOver = true;
                        break;
                    case "0":
                        // Game continues
                        break;
                    default:
                        JOptionPane.showMessageDialog(client.frame, "Error checking the game status.");

                }

                //call the web server to check if the game has been won or not yet if the player is forfeiting
                String gameState = client.proxy.getGameState(Integer.parseInt(client.GID));
                switch (gameState) {

                    case "1":
                        // Player 1 wins
                        // This message appears on opponents screen indicating the other player has forfeited
                        JOptionPane.showMessageDialog(client.frame, "The other player has forfeited, you win");
                        // Opponent is presented with option to go to main menu or quit
                        helperMethods.showOptions();
                        gameOver = true;
                        break;
                    case "2":
                        // Player 2 wins
                        //This message appears on the opponents screen indicating the other player has forfeited
                        JOptionPane.showMessageDialog(client.frame, "The other player has forfeited, you win");
                        //Opponent presented with option to go to main menu or quit 
                        helperMethods.showOptions();
                        gameOver = true;
                        break;
                    case "3":
                        // Player 2 wins
                        JOptionPane.showMessageDialog(client.frame, "It's a draw!");
                        //Opponent presented with option to go to main menu or quit
                        helperMethods.showOptions();
                        gameOver = true;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
