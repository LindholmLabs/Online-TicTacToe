/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.groupproject.tictactoeclient;

import com.formdev.flatlaf.FlatDarkLaf;
import com.groupproject.tictactoeclient.ContentPanes.CustomPanel;
import com.groupproject.tictactoeclient.ContentPanes.MainContentPanel;
import com.groupproject.tictactoeclient.ContentPanes.StartPanel;
import com.groupproject.tictactoeclient.ContentPanes.register;
import com.tttws.TicTacToeWS;
import com.tttws.TicTacToeWebService;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author William
 */
public class TicTacToeClient {

    private static TicTacToeClient client;
    private static TicTacToeWebService service;
    public static TicTacToeWS proxy;
    public JFrame frame;
    private CustomPanel CurrentPanel;
    public String UID = "";
    public String GID = "";
    public boolean OPPONENTS_TURN = true;
    public int NUM_OF_MOVES = 0;
    public String UID2;
    public String GID_Temp;
    public String HOST_UID = "";
    public String username;
    public String openGames;
    public int[][] BOARD;

    public static void main(String[] args) {
        // Enable flatlaf dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Could not initialize FlatLaf. Default swing look and feel will be used.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        // Initialize SOAP interface
        service = new TicTacToeWebService();
        proxy = service.getTicTacToeWSPort();

        // Initalize UI
        client = new TicTacToeClient();
    }

    public TicTacToeClient() {
        frame = new JFrame("TicTacToe"); // Create new Swing window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        //showPanel(new register(this));
        showPanel(new StartPanel(this));

        frame.setVisible(true);

        Thread t1 = new Thread(new PollingThread(this));
        t1.start();
    }

    public void showPanel(CustomPanel panel) {
        frame.getContentPane().removeAll(); // Clear current content
        CurrentPanel = panel;
        frame.getContentPane().add(panel);  // Add new panel
        frame.revalidate();                 // Refresh the frame
        frame.repaint();
    }

    public void refreshCurrentPanel() {
        CurrentPanel.refresh();
    }

    //reset the game data when user goes back to the main menu so the previous game doesnt show up and user can create a new game 
    public void resetGame() {
        this.BOARD = null;
        this.GID = "";
        this.UID2 = null;
        this.HOST_UID = "";
        this.NUM_OF_MOVES = 0;

        refreshCurrentPanel();
    }

    //used to log the user out and remove their user details
    public void logout() {
        this.UID = null;
        this.GID = "";
        this.UID2 = null;
        this.HOST_UID = "";
        this.NUM_OF_MOVES = 0;
        this.BOARD = new int[2][2];

    }

}
