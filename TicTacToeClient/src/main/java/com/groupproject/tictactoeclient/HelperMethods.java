/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient;

import com.groupproject.tictactoeclient.ContentPanes.MainContentPanel;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;


/**
 *
 * @author William
 */
public class HelperMethods {
    private TicTacToeClient client;
    
    public HelperMethods(TicTacToeClient client) {
        this.client = client;
    }
    
    public int[][] GetBoard(int gid) {
        System.out.println("Fetching board...");
        String response = client.proxy.getBoard(gid);
        System.out.println(response);
        
        if ("ERROR-NOMOVES".equals(response)) {
            return null;
        }
        
        // Split by lines and map each line to an integer array
        return Arrays.stream(response.split("\n"))
                     .map(row -> Arrays.stream(row.split(","))
                                       .mapToInt(Integer::parseInt)
                                       .toArray())
                     .toArray(int[][]::new);
    }
    
    public int[][] GetBoard() {
        if (client.GID.isBlank()) {
            return null;
        }
        
        return GetBoard(Integer.parseInt(client.GID));
    }
    
    public boolean IsHost() {
        return client.UID.equals(client.HOST_UID);
    }
    
    
    /**
     * Take the square with selected index
     * @param index square to take
     * @return return true if square was taken, false if it was already taken by opponent
     */
    public boolean TakeSqure(int index) {
        if ("0".equals(client.proxy.checkSquare(index % 3, (int) Math.floor(index / 3), Integer.parseInt(client.GID)))) {
            System.out.println("Taking square");
            client.proxy.takeSquare(index % 3, (int) Math.floor(index / 3), Integer.parseInt(client.GID), Integer.parseInt(client.UID));
            
            return true;
        } else {
            System.out.println("Cannot take square");
            return false;
        }
    }
    
    //https://www.geeksforgeeks.org/java-joptionpane/
    public void showOptions() {
            
        String[] options = {"Main Menu", "Quit"};

        // Display an option dialog with custom options
        // The user's choice is stored in the 'choice'
        // variable
        int choice = JOptionPane.showOptionDialog(
            null, // Parent component (null means center on screen)
            "Options", // Message to display
            "Custom Options", // Dialog title
            JOptionPane.YES_NO_CANCEL_OPTION, // Option type (Yes, No, Cancel)
            JOptionPane.QUESTION_MESSAGE, // Message type (question icon)
            null, // Custom icon (null means no custom icon)
            options, // Custom options array
            options[0] // Initial selection (default is "Cancel")
        );

        // Check the user's choice and display a
        // corresponding message
        if (choice == JOptionPane.YES_OPTION) {
            // If the user chose 'Yes'
            // reset their game and put them back to the main menu
            client.resetGame();
            client.showPanel(new MainContentPanel(client));
        }
        else if (choice == JOptionPane.NO_OPTION) {
            // If the user chose 'No'
            // quit the application
            
            System.exit(0);
        }
        else {
            // If the user chose 'Cancel' or closed the
            // dialog
            // show a message indicating the operation is
            // canceled
            JOptionPane.showMessageDialog(null, "Operation canceled.");
        }
    
    }
}
