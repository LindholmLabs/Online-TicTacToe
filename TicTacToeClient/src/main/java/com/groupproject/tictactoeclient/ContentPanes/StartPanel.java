/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 *
 * @author Adam 
 */
public class StartPanel extends JPanel{
    public StartPanel(TicTacToeClient client) {
        
        // Set panel layout to spring layout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        //Create the 2 buttons login and register
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        
        //Add the Buttons to the panel
        add(loginButton);
        add(registerButton);
        
        //Login Button constraints
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginButton, -60, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, loginButton, 0, SpringLayout.VERTICAL_CENTER, this);
        
        //Register Button constraints
        layout.putConstraint(SpringLayout.WEST, registerButton, 10, SpringLayout.EAST, loginButton);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, registerButton, 0, SpringLayout.VERTICAL_CENTER, this);
 
        
        
        //Login Button Action - go to login page
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.showPanel(new login(client));
            }
        });
        
        //Register Button Action - go to the register page
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.showPanel(new register(client));
            }
        });
    }
}
