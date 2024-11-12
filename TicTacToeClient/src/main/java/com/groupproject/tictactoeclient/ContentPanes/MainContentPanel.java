/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.Timer;

/**
 *
 * @author William and Adam 
 * Contains the content visible in the main program.
 */

        
public class MainContentPanel extends CustomPanel {
    
    
    TicTacToeClient client;
    JList<String> openGamesList;
    
    private JLabel gameTimerLabel;
    private Timer timer;
    private int remainingTime;

    
    public MainContentPanel(TicTacToeClient client) {
        
        this.client = client;
        
        // Set panel layout to spring layout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        // Create panel components
        JButton createGameButton = new JButton("New Game");
        JButton scoreBoardButton = new JButton("Scoreboard");
        JButton joinGameButton = new JButton("Join Game");
        openGamesList = new JList<>();
        JScrollPane openGamesScrollPane = new JScrollPane(openGamesList);
        TicTacToeGrid grid = new TicTacToeGrid();
        JLabel gameStatusLabel = new JLabel();
        gameTimerLabel = new JLabel();
        JButton viewScoreButton = new JButton("View Personal Score");
        
        // set mock data for labels:
        gameStatusLabel.setText("Opponents turn");
        gameTimerLabel.setText("15m 0s");

        
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
        
        
        //Create game button action listener 
        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.proxy.newGame(Integer.parseInt(client.UID));
                
                //start countdown when create game
                startTimer();
            }
        });
    }

    @Override
    public void refresh() {
        System.out.println("Refreshing MainContentPanel");
        var games = client.openGames.split(",");
        System.out.println("Current Games: " + client.openGames);
        openGamesList.setListData(games);
        this.revalidate();
        this.repaint();
    }
    
    
    private void startTimer()
    {
        //remaining time is 15 minutes
        remainingTime = 1 * 60;
                
        //create timer that updates every 1 seconds i.e countdown clock
        timer = new Timer(1000, new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                
                //countdown time
                remainingTime--;
                
                int minutes = remainingTime / 60;
                int seconds = remainingTime % 60;
                
                String time = String.format("%02d:%02d", minutes, seconds);
                
                gameTimerLabel.setText(time);
                
                //need to implement when no one else joins the game 
                //and the countdown is finished that the game is then deleted
                //at the moment it just prints game finished
                if(remainingTime <= 0) {
                    timer.stop();
                    gameTimerLabel.setText("Finished");
                }
            }
        });
           timer.start();
    }
    
    
    
}