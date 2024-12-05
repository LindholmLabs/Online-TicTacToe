package com.groupproject.tictactoeclient.ContentPanes;

import com.groupproject.tictactoeclient.TicTacToeClient;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;
import javax.swing.JOptionPane;

/**
 *
 * Author: Luke
 */
public class register extends CustomPanel {

    public register(TicTacToeClient client) {

        // Set panel layout to spring layout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        //Create the login Button
        JButton registerButton = new JButton("Register");

        //Create the username and password Labels
        JLabel usernameLabel = new JLabel("username");
        JLabel passwordLabel = new JLabel("password");
        JLabel nameLabel = new JLabel("name");
        JLabel surnameLabel = new JLabel("surname");

        //Create the username and password text fields
        JTextField usernameTextField = new JTextField(20);
        JPasswordField passwordTextField = new JPasswordField(20);
        JTextField nameTextField = new JTextField(20);
        JTextField surnameTextField = new JTextField(20);

        // Back button
        JButton backButton = new JButton("Back");

        //Create a checkbox to show the password
        JCheckBox showPassword = new JCheckBox("show password");

        //Create error message if register is not correct
        JLabel registerErrorLabel = new JLabel("User is already created");
        registerErrorLabel.setForeground(Color.red);
        registerErrorLabel.setVisible(false);

        // Add components to panel
        add(registerButton);
        add(usernameLabel);
        add(passwordLabel);
        add(nameLabel);
        add(surnameLabel);
        add(usernameTextField);
        add(passwordTextField);
        add(nameTextField);
        add(surnameTextField);

        add(showPassword);
        add(backButton);
        add(registerErrorLabel);

        // UsernameLabel constraints
        layout.putConstraint(SpringLayout.EAST, usernameLabel, -20, SpringLayout.WEST, usernameTextField);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, usernameLabel, 0, SpringLayout.VERTICAL_CENTER, usernameTextField);

        //Username textfield Constraint
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, usernameTextField, -60, SpringLayout.VERTICAL_CENTER, this);

        //nameLabel constraints
        layout.putConstraint(SpringLayout.EAST, nameLabel, -20, SpringLayout.WEST, nameTextField);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, nameLabel, 0, SpringLayout.VERTICAL_CENTER, nameTextField);

        //name textfield constraints
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, nameTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, nameTextField, 20, SpringLayout.SOUTH, usernameTextField);

        //surnameLabel constraints
        layout.putConstraint(SpringLayout.EAST, surnameLabel, -20, SpringLayout.WEST, surnameTextField);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, surnameLabel, 0, SpringLayout.VERTICAL_CENTER, surnameTextField);

        //surname textfield constraints
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, surnameTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, surnameTextField, 20, SpringLayout.SOUTH, nameTextField);

        //PasswordLabel constraints
        layout.putConstraint(SpringLayout.EAST, passwordLabel, -20, SpringLayout.WEST, passwordTextField);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, passwordLabel, 0, SpringLayout.VERTICAL_CENTER, passwordTextField);

        //Password textfield constraint
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, passwordTextField, 20, SpringLayout.SOUTH, surnameTextField);

        //Show password checkbox constraint
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, showPassword, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, showPassword, 20, SpringLayout.SOUTH, passwordTextField);

        //Register Button constraint
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, registerButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, registerButton, 20, SpringLayout.SOUTH, showPassword);

        //Back button constraint
        layout.putConstraint(SpringLayout.WEST, backButton, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, backButton, 20, SpringLayout.NORTH, this);

        //Error label constraint
        layout.putConstraint(SpringLayout.EAST, registerErrorLabel, -20, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, registerErrorLabel, 20, SpringLayout.NORTH, this);

        // Action listener for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //get the text from each textfield
                String username = usernameTextField.getText().trim();
                String name = nameTextField.getText().trim();
                String surname = surnameTextField.getText().trim();
                String password = passwordTextField.getText().trim();

                //check if any of the fields are empty to prevent a blank user being created
                if (username.isEmpty() || name.isEmpty() || surname.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(client.frame, "Please fill in all fields");
                    return;
                }

                // stores the UID in responce and stores it as a global variable
                String response = client.proxy.register(usernameTextField.getText(), passwordTextField.getText(), nameTextField.getText(), surnameTextField.getText());
                System.out.println(response);


                //check if the username already exists, if not allow user to register
                if ("ERROR-REPEAT".equals(response)) {
                    JOptionPane.showMessageDialog(client.frame, "User already exists");
                    return;
                } else {


                    //register the user and assign a User id to the client
                    client.UID = response;
                    //stores the username 
                    client.username = usernameTextField.getText();
                     
                    

                    System.out.println("Client.UID = " + client.UID);
                    //once registered move to the main panel
                    client.showPanel(new MainContentPanel(client));
                }
            }

        });

        //Item listener to check if the showPassword checkbox has been selected
        //If selected show the password and if not display the password as **** normal
        char userPassword = passwordTextField.getEchoChar();
        showPassword.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordTextField.setEchoChar((char) 0);
                } else {
                    passwordTextField.setEchoChar(userPassword);
                }
            }
        });

        // Back button listener - go back to the start panel 
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.showPanel(new StartPanel(client));
            }
        });

    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
