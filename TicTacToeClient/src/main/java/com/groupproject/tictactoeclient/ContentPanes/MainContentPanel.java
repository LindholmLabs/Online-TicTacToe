package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;

public class MainContentPanel extends CustomPanel {

    TicTacToeClient client;
    JList<String> openGamesList;
    private JLabel gameTimerLabel;
    private Timer timer;
    private int remainingTime;
    private TicTacToeGrid grid;
    private JLabel gameStatusLabel;
    private JLabel currentGameLabel;
    private String[] previousRawGames;

    public MainContentPanel(TicTacToeClient client) {
        this.client = client;

        // Set panel layout to SpringLayout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // Create panel components
        JButton createGameButton = new JButton("New Game");
        JButton allscoreButton = new JButton("Scoreboard");
        JButton joinGameButton = new JButton("Join Game");
        JButton forfeitButton = new JButton("Forfeit Game");
        JButton logoutButton = new JButton("Logout");

        openGamesList = new JList<>();
        JScrollPane openGamesScrollPane = new JScrollPane(openGamesList);
        grid = new TicTacToeGrid(client, 3, 3); // A 3x3 TicTacToe grid
        gameStatusLabel = new JLabel();
        gameTimerLabel = new JLabel();
        currentGameLabel = new JLabel();
        JButton scoreButton = new JButton("View Personal Score");

        client.frame.setTitle("TicTacToe - Welcome " + client.username);

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
        add(currentGameLabel);
        add(forfeitButton);
        add(logoutButton);

        layout.putConstraint(SpringLayout.WEST, createGameButton, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, createGameButton, 10, SpringLayout.NORTH, this);

        //forfeit button
        layout.putConstraint(SpringLayout.EAST, forfeitButton, -160, SpringLayout.EAST, grid);
        layout.putConstraint(SpringLayout.SOUTH, forfeitButton, -20, SpringLayout.SOUTH, this);

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

        //Username label 
        layout.putConstraint(SpringLayout.WEST, currentGameLabel, 0, SpringLayout.WEST, grid);
        layout.putConstraint(SpringLayout.NORTH, currentGameLabel, -35, SpringLayout.NORTH, grid);

        layout.putConstraint(SpringLayout.EAST, gameTimerLabel, 0, SpringLayout.EAST, grid);
        layout.putConstraint(SpringLayout.NORTH, gameTimerLabel, -20, SpringLayout.NORTH, grid);

        layout.putConstraint(SpringLayout.WEST, grid, 40, SpringLayout.EAST, allscoreButton);
        layout.putConstraint(SpringLayout.NORTH, grid, 40, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, grid, 240, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, grid, -20, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.SOUTH, logoutButton, -80, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, logoutButton, -20, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.SOUTH, scoreButton, -20, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, scoreButton, -20, SpringLayout.EAST, this);

        openGamesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && openGamesList.getSelectedValue() != null) {
                String selectedGame = openGamesList.getSelectedValue();

                String[] parts = selectedGame.split(" ");
                if (parts.length >= 2) {
                    String gameID = parts[1];  // The GID is the second part
                    // Debug: Print the extracted GID

                    // Store the game ID to use in the joinGame action
                    client.GID_Temp = gameID;

                } else {
                    // Handle cases where the format doesn't match
                }
            }
        });

        createGameButton.addActionListener(e -> {
            if (!client.HOST_UID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You cannot create a new game since you already have one.", "NOO.", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            client.GID = client.proxy.newGame(Integer.parseInt(client.UID));

            if (client.GID != null && !client.GID.isEmpty()) {
                client.HOST_UID = client.UID;
                client.OPPONENTS_TURN = false;
                String newGameData = "Game " + client.GID + " Host: " + client.UID;
                if (client.openGames == null || client.openGames.isEmpty()) {
                    client.openGames = newGameData;
                } else {
                    client.openGames += "\n" + newGameData;  //seperates each new game
                }
            }
            startTimer();
            refresh();  // refresh the list to show if there is a new game added
        });

        joinGameButton.addActionListener(e -> {
            // join the game using GID
            if (client.GID_Temp != null) {
                if (!client.UID.equals(client.HOST_UID)) {
                    client.resetGame();
                    client.UID2 = client.proxy.joinGame(Integer.parseInt(client.UID), Integer.parseInt(client.GID_Temp));
                    client.GID = client.GID_Temp;
                    client.HOST_UID = "";
                }
            } else {
                System.out.println("no game selected.");
            }
        });

        //logout button 
        logoutButton.addActionListener(e -> {

            //reset the client info 
            client.logout();

            //go back to the start panel 
            client.showPanel(new login(client));

        });

        
        
        //forfeit button action listener 
        forfeitButton.addActionListener(e -> {
            if(client.UID.equals(client.HOST_UID))
            {
                client.proxy.setGameState(Integer.parseInt(client.GID), 2);
                
            }            
            else 
            { 
                client.proxy.setGameState(Integer.parseInt(client.GID), 1);
            }
            
            client.resetGame();
            client.showPanel(new MainContentPanel(client));
            refresh();
           
        });
        
        


        scoreButton.addActionListener(e -> {
            client.showPanel(new score(client));
            
        });

        allscoreButton.addActionListener(e -> {
            client.showPanel(new allscore(client));
            
        });
    }

    @Override
    public void refresh() {
        // System.out.println("Raw client.openGames data: " + client.openGames);  // Debugging output

        grid.refresh();

        if (client.OPPONENTS_TURN) {
            gameStatusLabel.setText("Opponents turn!");
        } else {
            gameStatusLabel.setText("Your turn!");
        }

        // Check if openGames is empty/null, if there is nothin then display nothing in the box
        if (client.openGames == null || client.openGames.isEmpty()) {
            openGamesList.setListData(new String[0]);
        } else {
            String[] rawGames = client.openGames.split("\n");

            if (previousRawGames == null) {
                previousRawGames = new String[0];
            }

            String[] formattedGames = new String[rawGames.length];

            //extract the GID, Host, and time (mabye add time later or just data or just time)
            for (int i = 0; i < rawGames.length; i++) {
                String gameData = rawGames[i].trim();
                String[] parts = gameData.split(",");

                if (parts.length == 3) {
                    String gameID = parts[0].trim();
                    String hostID = parts[1].trim();
                    String timestamp = parts[2].trim(); //use later mabye?

                    // formating the output in the box
                    formattedGames[i] = "Game " + gameID + " Host: " + hostID;
                } else {
                    formattedGames[i] = " "; //set the box to nothing the format isnt right i.e if there is no host name
                }
            }

            if (rawGames.length != previousRawGames.length) {
                openGamesList.setListData(formattedGames);
            }
            previousRawGames = rawGames;

            //set userNameLabel as the current user logged in 
            currentGameLabel.setText("Current Game  : " + client.GID);
        }
    }

    private void startTimer() {
        // Remaining time is 15 minutes
        //remainingTime = 1 * 15;

        //commented out as longer duration
        remainingTime = 15 * 60;

        // Create a timer that updates every second
        timer = new Timer(1000, e -> {
            // Countdown time
            timer.start();
            remainingTime--;

            int minutes = remainingTime / 60;
            int seconds = remainingTime % 60;

            String time = String.format("%02d:%02d", minutes, seconds);

            gameTimerLabel.setText(time);

            if (client.NUM_OF_MOVES >= 2) {
                timer.restart();
                timer.stop();

            }

            if (remainingTime <= 0) {
                timer.stop();
                gameTimerLabel.setText("No opponent, please try again");

                String deleteGame = client.proxy.deleteGame(Integer.parseInt(client.GID), Integer.parseInt(client.UID));

                System.out.println(deleteGame);
                client.resetGame();
                client.showPanel(new MainContentPanel(client));
                refresh();

            }
        });
        timer.start();
    }
}
