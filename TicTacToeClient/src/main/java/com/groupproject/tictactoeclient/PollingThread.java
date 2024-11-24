/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Willi
 */
public class PollingThread implements Runnable {
    private TicTacToeClient client;
    private HelperMethods helperMethods;
    
    public PollingThread(TicTacToeClient client) {
        this.client = client;
        helperMethods = new HelperMethods(client);
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                
                if (client.UID == null || Integer.parseInt(client.UID) == -1) {
                    continue;
                }
                
                client.BOARD = helperMethods.GetBoard();
                
                System.out.println("Polling thread executing...");
                client.openGames = client.proxy.showOpenGames();
                System.out.println("Result: " + client.openGames);
                client.refreshCurrentPanel();
            } catch (Exception e) {
                e.printStackTrace();
            }  
        }
    }
}
