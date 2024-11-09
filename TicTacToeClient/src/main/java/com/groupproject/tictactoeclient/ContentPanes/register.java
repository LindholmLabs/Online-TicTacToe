package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Box;

/**
 *
 * Author: Willi
 */
public class register extends JPanel {
    public register(TicTacToeClient client) {
        // Set the layout to BoxLayout for vertical alignment
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Username label and very small text field
JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(600, 20));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        add(usernamePanel);

        // Password label and very small text field
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField(15);
        passwordField.setMaximumSize(new Dimension(600, 20)); // Forces small width
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        add(passwordPanel);

        // Register button
        JButton registerButton = new JButton("Register");
        add(Box.createVerticalStrut(10)); // Add space between fields and button
        add(registerButton);

        // Action listener for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.showPanel(new StartPanel(client));
            }
        });
    }
}
