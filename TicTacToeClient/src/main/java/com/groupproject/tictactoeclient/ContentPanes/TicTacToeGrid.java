/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.HelperMethods;
import com.groupproject.tictactoeclient.TicTacToeClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author William
 */
class TicTacToeGrid extends JPanel {

    TicTacToeClient client;
    ArrayList<JButton> gridButtons;
    HelperMethods helperMethods;

    public TicTacToeGrid(TicTacToeClient client, int rows, int cols) {
        this.client = client;
        helperMethods = new HelperMethods(client);
        setLayout(new GridLayout(rows, cols, 5, 5));
        gridButtons = new ArrayList<JButton>();

        for (int i = 0; i < rows * cols; i++) {
            final int index = i;
            JButton button = new JButton();
            gridButtons.add(button);
            button.setFont(new Font("Arial", Font.BOLD, 32));
            add(button);

            button.addActionListener(e -> {
                //need to add an if statment that checks the GID 
                //client.UID2 = client.proxy.leagueTable();
                if (client.UID.equals(client.HOST_UID)) {
                    if (helperMethods.TakeSqure(index)) {
                        button.setText("X");
                    }
                } else {
                    if (helperMethods.TakeSqure(index)) {
                        button.setText("O");
                    }
                }
                button.setEnabled(false); // prevents the player from clicking the same spot again
            });
        }
    }

    public void refresh() {
        System.out.println("UID: " + client.UID + ", HOST_UID: " + client.HOST_UID);
        if (client.BOARD == null) {
            return;
        }
        
        System.out.println("Updating TicTacToeGrid...");
        
        final int[][] board = client.BOARD;
        
        for (int[] row : board) {
            // Convert grid coordinates to proper index
            int index = (row[2] * 3) + row[1];
            
            // if there is no host player id saved save it from detected move
            if (!String.valueOf(row[0]).equals(client.UID) && client.HOST_UID.isBlank()) {
                client.HOST_UID = String.valueOf(row[0]);
            } 
            
            if (String.valueOf(row[0]).equals(client.HOST_UID)) {
                gridButtons.get(index).setText("X");
                gridButtons.get(index).setEnabled(false);
            } else {
                gridButtons.get(index).setText("O");
                gridButtons.get(index).setEnabled(false);
            }
        }
        
        this.revalidate();
        this.repaint();
    }

}