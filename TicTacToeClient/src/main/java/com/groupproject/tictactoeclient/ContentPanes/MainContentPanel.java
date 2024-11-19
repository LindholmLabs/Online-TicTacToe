package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MainContentPanel extends CustomPanel {

    TicTacToeClient client;
    JList<String> openGamesList;

    private JLabel gameTimerLabel;
    private Timer timer;
    private int remainingTime;

    public MainContentPanel(TicTacToeClient client) {
        this.client = client;

        // Set panel layout to SpringLayout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // Create panel components
        JButton createGameButton = new JButton("New Game");
        JButton allscoreButton = new JButton("Scoreboard");
        JButton joinGameButton = new JButton("Join Game");
        openGamesList = new JList<>();
        JScrollPane openGamesScrollPane = new JScrollPane(openGamesList);
        TicTacToeGrid grid = new TicTacToeGrid(3, 3); // A 3x3 TicTacToe grid
        JLabel gameStatusLabel = new JLabel();
        gameTimerLabel = new JLabel();
        JButton scoreButton = new JButton("View Personal Score");

        // Set mock data for labels
        gameStatusLabel.setText("Opponent's turn");
        gameTimerLabel.setText("15m 0s");

        // Add buttons to panel
        add(allscoreButton);
        add(createGameButton);
        add(openGamesScrollPane);
        add(joinGameButton);
        add(grid);
        add(gameStatusLabel);
        add(gameTimerLabel);
        add(scoreButton);

        
        layout.putConstraint(SpringLayout.WEST, createGameButton, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, createGameButton, 10, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, allscoreButton, 20, SpringLayout.EAST, createGameButton);
        layout.putConstraint(SpringLayout.NORTH, allscoreButton, 0, SpringLayout.NORTH, createGameButton);

        layout.putConstraint(SpringLayout.WEST, openGamesScrollPane, 0, SpringLayout.WEST, createGameButton);
        layout.putConstraint(SpringLayout.EAST, openGamesScrollPane, 0, SpringLayout.EAST, allscoreButton);
        layout.putConstraint(SpringLayout.NORTH, openGamesScrollPane, 10, SpringLayout.SOUTH, createGameButton);
        layout.putConstraint(SpringLayout.SOUTH, openGamesScrollPane, -10, SpringLayout.NORTH, joinGameButton);

        layout.putConstraint(SpringLayout.WEST, joinGameButton, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, joinGameButton, -20, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, joinGameButton, 0, SpringLayout.EAST, allscoreButton);

        layout.putConstraint(SpringLayout.WEST, gameStatusLabel, 0, SpringLayout.WEST, grid);
        layout.putConstraint(SpringLayout.NORTH, gameStatusLabel, -20, SpringLayout.NORTH, grid);
        layout.putConstraint(SpringLayout.EAST, gameTimerLabel, 0, SpringLayout.EAST, grid);
        layout.putConstraint(SpringLayout.NORTH, gameTimerLabel, -20, SpringLayout.NORTH, grid);

        layout.putConstraint(SpringLayout.WEST, grid, 40, SpringLayout.EAST, allscoreButton);
        layout.putConstraint(SpringLayout.NORTH, grid, 40, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, grid, 240, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, grid, -20, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.SOUTH, scoreButton, -20, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, scoreButton, -20, SpringLayout.EAST, this);

        // Button action listeners
        createGameButton.addActionListener(e -> {
            client.proxy.newGame(Integer.parseInt(client.UID));
            startTimer();
        });

        joinGameButton.addActionListener(e -> {
            //client.proxy.joinGame(Integer.parseInt(client.UID), Integer.parseInt(client.GID));
            //startTimer();
        });

        scoreButton.addActionListener(e -> {
            client.showPanel(new score(client));
            startTimer();
        });

        allscoreButton.addActionListener(e -> {
            client.showPanel(new allscore(client));
            startTimer();
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

    private void startTimer() {
        // Remaining time is 15 minutes
        remainingTime = 15 * 60;

        // Create a timer that updates every second
        timer = new Timer(1000, e -> {
            // Countdown time
            remainingTime--;

            int minutes = remainingTime / 60;
            int seconds = remainingTime % 60;

            String time = String.format("%02d:%02d", minutes, seconds);

            gameTimerLabel.setText(time);

            if (remainingTime <= 0) {
                timer.stop();
                gameTimerLabel.setText("Finished");
            }
        });
        timer.start();
    }

   
    class TicTacToeGrid extends JPanel {
        public TicTacToeGrid(int rows, int cols) {
            setLayout(new GridLayout(rows, cols, 5, 5)); // 

            for (int i = 0; i < rows * cols; i++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 32)); 
                add(button);

                
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button.setText("X");
                        button.setEnabled(false); // so player cant click again
                    }
                });
            }
        }
    }
}
