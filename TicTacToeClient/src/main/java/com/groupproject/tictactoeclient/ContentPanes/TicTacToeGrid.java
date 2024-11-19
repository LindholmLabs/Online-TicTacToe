/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient.ContentPanes;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author William
 */
 class TicTacToeGrid extends JPanel {
        public TicTacToeGrid(int rows, int cols) {
            setLayout(new GridLayout(rows, cols, 5, 5));

            for (int i = 0; i < rows * cols; i++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 32));
                add(button);

                button.addActionListener(e -> {
                    //need to add an if statment that checks the GID 
                    button.setText("X");
                    button.setEnabled(false); // prevents the player from clicking the same spot again
                });
            }
        }
    

    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 }