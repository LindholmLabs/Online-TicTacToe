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
 * @author Willi
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
                
                
                //call the web server to check if the game has been won or not yet
                String result = client.proxy.checkWin(Integer.parseInt(client.GID));
                System.out.println("checkWin: " + result);
                //if the game is not over check the result of the game
                //if (!gameOver) {
                    System.out.println("gameOver not working");
                    switch (result) {
                        

                        case "1":
                            // Player 1 wins
                            JOptionPane.showMessageDialog(client.frame, "Player 1 wins!");
                            client.proxy.setGameState(Integer.parseInt(client.GID), 1);
                            helperMethods.showOptions();
                            gameOver = true;
                            break;
                        case "2":
                            // Player 2 wins
                            JOptionPane.showMessageDialog(client.frame, "Player 2 wins!");
                            client.proxy.setGameState(Integer.parseInt(client.GID), 2);
                            helperMethods.showOptions();
                            gameOver = true;
                            break;
                        case "3":
                            // It's a draw
                            JOptionPane.showMessageDialog(client.frame, "It's a draw!");
                            client.proxy.setGameState(Integer.parseInt(client.GID), 3);
                            helperMethods.showOptions();
                            gameOver = true;
                            break;
                        case "0":
                            // Game continues
                            break;
                        default:
                            JOptionPane.showMessageDialog(client.frame, "Error checking the game status.");

                    }
                //}

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
