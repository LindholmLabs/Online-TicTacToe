/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient.ContentPanes;
import com.groupproject.tictactoeclient.TicTacToeClient;

import java.awt.event.*;

import javax.swing.*;
/**
 *
 * @author adam
 */
public class login extends JPanel {
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
        
        //Create a checkbox to show the password
        JCheckBox showPassword = new JCheckBox("show password");
        
        add(loginButton);
        add(usernameLabel);
        add(passwordLabel);
        add(usernameTextField);
        add(passwordTextField);
        add(showPassword);
        
        
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
        
        
        
       // Login Button Action - check the entries of the textFields 
        loginButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               client.showPanel(new login(client));


           }
       });
        
        //Item listener to check if the showPassword checkbox has been selected
        //If selected show the password and if not display the password as **** normal
        char defaultPassword = passwordTextField.getEchoChar();
        // Login Button Action - check the entries of the textFields 
        showPassword.addItemListener(new ItemListener() {
           public void itemStateChanged(ItemEvent e) {
              if (e.getStateChange() == ItemEvent.SELECTED) {
                  passwordTextField.setEchoChar((char) 0); 
                } else {
            passwordTextField.setEchoChar(defaultPassword);
              }
           }
       });
        
        
        
    }
}
