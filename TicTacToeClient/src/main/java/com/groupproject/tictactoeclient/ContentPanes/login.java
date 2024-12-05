/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import java.awt.Color;

import java.awt.event.*;

import javax.swing.*;

/**
 *
 * @author adam
 */
public class login extends CustomPanel {

    public login(TicTacToeClient client) {

        // Set panel layout to spring layout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        //Create the login Button
        JButton loginButton = new JButton("Login");

        //Create the username and password Labels
        JLabel usernameLabel = new JLabel("username");
        JLabel passwordLabel = new JLabel("password");

        //Create the username and password text fields
        JTextField usernameTextField = new JTextField(20);
        JPasswordField passwordTextField = new JPasswordField(20);

        // Back button
        JButton backButton = new JButton("Back");

        //Create a checkbox to show the password
        JCheckBox showPassword = new JCheckBox("show password");

        //Create error message if login is not correct
        JLabel loginErrorLabel = new JLabel("Wrong username or password");
        loginErrorLabel.setForeground(Color.red);
        loginErrorLabel.setVisible(false);

        // Add components to panel
        add(loginButton);
        add(usernameLabel);
        add(passwordLabel);
        add(usernameTextField);
        add(passwordTextField);
        add(showPassword);
        add(backButton);
        add(loginErrorLabel);

        // UsernameLabel constraints
        layout.putConstraint(SpringLayout.EAST, usernameLabel, -20, SpringLayout.WEST, usernameTextField);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, usernameLabel, 0, SpringLayout.VERTICAL_CENTER, usernameTextField);

        //Username textfield Constraint
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, usernameTextField, 0, SpringLayout.VERTICAL_CENTER, this);

        //PasswordLabel constraints
        layout.putConstraint(SpringLayout.EAST, passwordLabel, -20, SpringLayout.WEST, passwordTextField);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, passwordLabel, 0, SpringLayout.VERTICAL_CENTER, passwordTextField);

        //Password textfield constraint
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, passwordTextField, 20, SpringLayout.SOUTH, usernameTextField);

        //Show password checkbox constraint
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, showPassword, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, showPassword, 20, SpringLayout.SOUTH, passwordTextField);

        //Login Button constraint
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, loginButton, 20, SpringLayout.SOUTH, showPassword);

        //Back button constraint
        layout.putConstraint(SpringLayout.WEST, backButton, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, backButton, 20, SpringLayout.NORTH, this);

        //Error label constraint
        layout.putConstraint(SpringLayout.EAST, loginErrorLabel, -20, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, loginErrorLabel, 20, SpringLayout.NORTH, this);

        // Login Button Action - check the entries of the textFields 
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();  // Get the username
                client.username = username;
                client.UID = String.valueOf(client.proxy.login(usernameTextField.getText(), String.valueOf(passwordTextField.getPassword())));
                System.out.println("UID = " + client.UID);

                if (Integer.parseInt(client.UID) == -1) {
                    loginErrorLabel.setVisible(true);
                } else {
                    client.showPanel(new MainContentPanel(client));
                }
            }
        });

        //Item listener to check if the showPassword checkbox has been selected
        //If selected show the password and if not display the password as **** normal
        char defaultPassword = passwordTextField.getEchoChar();
        showPassword.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordTextField.setEchoChar((char) 0);
                } else {
                    passwordTextField.setEchoChar(defaultPassword);
                }
            }
        });

        // Back button listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.showPanel(new StartPanel(client));
            }
        });
    }

    @Override
    public void refresh() {
        // not needed
    }
}
