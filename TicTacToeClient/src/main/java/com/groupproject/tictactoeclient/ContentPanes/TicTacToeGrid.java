/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient.ContentPanes;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Willi
 */
public class TicTacToeGrid extends JPanel {
    JButton[][] grid = new JButton[3][3];
    
    public TicTacToeGrid() {
        
        setLayout(new GridLayout(3, 3));
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                grid[row][col] = new JButton("");
                grid[row][col].setBackground(new Color(45, 45, 45));  // Dark gray for FlatLaf Dark theme
                grid[row][col].setForeground(Color.WHITE);  // Set font color to white
                grid[row][col].setOpaque(true);
                grid[row][col].setBorder(new LineBorder(new Color(70, 70, 70), 1));  // Soft, light gray border
                grid[row][col].setOpaque(true);
                add(grid[row][col]);
            }
        }
    }
}
