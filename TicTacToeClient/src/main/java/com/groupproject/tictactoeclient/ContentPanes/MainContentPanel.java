/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

/**
 *
 * @author Willi
 * Contains the content visible in the main program.
 */
public class MainContentPanel extends JPanel {
    public MainContentPanel(TicTacToeClient client) {
        
        // Set panel layout to spring layout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        // Create panel components
        JButton createGameButton = new JButton("New Game");
        JButton scoreBoardButton = new JButton("Scoreboard");
        JButton joinGameButton = new JButton("Join Game");
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"}; // mock list
        JList<String> openGamesList = new JList<>(items);
        JScrollPane openGamesScrollPane = new JScrollPane(openGamesList);
        TicTacToeGrid grid = new TicTacToeGrid();
        JLabel gameStatusLabel = new JLabel();
        JLabel gameTimerLabel = new JLabel();
        JButton viewScoreButton = new JButton("View Personal Score");
        
        // set mock data for labels:
        gameStatusLabel.setText("Opponents turn");
        gameTimerLabel.setText("12m 32s");

        
        // Add buttons to panel
        add(scoreBoardButton);
        add(createGameButton);
        add(openGamesScrollPane);
        add(joinGameButton);
        add(grid);
        add(gameStatusLabel);
        add(gameTimerLabel);
        add(viewScoreButton);
        
        // Add sprint layout constraints
        // create game button constraints
        layout.putConstraint(SpringLayout.WEST, createGameButton, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, createGameButton, 10, SpringLayout.NORTH, this);
        
        // scoreboard button constraints
        layout.putConstraint(SpringLayout.WEST, scoreBoardButton, 20, SpringLayout.EAST, createGameButton);
        layout.putConstraint(SpringLayout.NORTH, scoreBoardButton, 0, SpringLayout.NORTH, createGameButton);
        
        // Games list constraints
        layout.putConstraint(SpringLayout.WEST, openGamesScrollPane, 0, SpringLayout.WEST, createGameButton);
        layout.putConstraint(SpringLayout.EAST, openGamesScrollPane, 0, SpringLayout.EAST, scoreBoardButton);
        layout.putConstraint(SpringLayout.NORTH, openGamesScrollPane, 10, SpringLayout.SOUTH, createGameButton);
        layout.putConstraint(SpringLayout.SOUTH, openGamesScrollPane, -10, SpringLayout.NORTH, joinGameButton);
        
        // join button constraints
        layout.putConstraint(SpringLayout.WEST, joinGameButton, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, joinGameButton, -20, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, joinGameButton, 0, SpringLayout.EAST, scoreBoardButton);
        
        // Add label constraints
        layout.putConstraint(SpringLayout.WEST, gameStatusLabel, 0, SpringLayout.WEST, grid);
        layout.putConstraint(SpringLayout.NORTH, gameStatusLabel, -20, SpringLayout.NORTH, grid);
        layout.putConstraint(SpringLayout.EAST, gameTimerLabel, 0, SpringLayout.EAST, grid);
        layout.putConstraint(SpringLayout.NORTH, gameTimerLabel, -20, SpringLayout.NORTH, grid);
        
        // grid constraints
        layout.putConstraint(SpringLayout.WEST, grid, 40, SpringLayout.EAST, scoreBoardButton);
        layout.putConstraint(SpringLayout.NORTH, grid, 40, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, grid, 240, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, grid, -20, SpringLayout.EAST, this);
        
        // View score button constraints
        layout.putConstraint(SpringLayout.SOUTH, viewScoreButton, -20, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, viewScoreButton, -20, SpringLayout.EAST, this);
    }
}