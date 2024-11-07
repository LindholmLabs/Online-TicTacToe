/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.groupproject.tictactoeclient;
import com.formdev.flatlaf.FlatDarkLaf;
import com.groupproject.tictactoeclient.ContentPanes.MainContentPanel;
import com.tttws.TicTacToeWS;
import com.tttws.TicTacToeWebService;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Willi
 */
public class TicTacToeClient {
    private static TicTacToeClient client;
    private static TicTacToeWebService service;
    private static TicTacToeWS proxy;
    private JFrame frame;

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
        
        // Initalize UI
        client = new TicTacToeClient();
    }
    
    public TicTacToeClient() {
        
        
        frame = new JFrame("TicTacToe"); // Create new Swing window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        showPanel(new MainContentPanel(this));
        
        frame.setVisible(true);
    }
    
    public void showPanel(JPanel panel) {
        frame.getContentPane().removeAll(); // Clear current content
        frame.getContentPane().add(panel);  // Add new panel
        frame.revalidate();                 // Refresh the frame
        frame.repaint();
    }
}