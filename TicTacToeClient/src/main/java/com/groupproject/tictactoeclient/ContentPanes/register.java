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
 * Author: Luke
 */
public class register extends CustomPanel {
    public register(TicTacToeClient client) {
        // Set the layout to BoxLayout for vertical alignment
         setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Username 
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(600, 20));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        add(usernamePanel);

        // Password 
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField(15);
        passwordField.setPreferredSize(new Dimension(600, 20));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        add(passwordPanel);
        
        // Name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(10); // adjusting width
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // Surname
        JLabel surnameLabel = new JLabel("Surname:");
        JTextField surnameField = new JTextField(10); 
        namePanel.add(surnameLabel);
        namePanel.add(surnameField);
        
        
        add(namePanel);

        // Register button
        JButton registerButton = new JButton("Register");
        add(Box.createVerticalStrut(10)); 
        add(registerButton);

        // Action listener for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.UID = client.proxy.register(usernameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText());
                client.username = usernameField.getText(); 
                System.out.println(client.UID);
                client.showPanel(new MainContentPanel(client));
            }
        });
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}