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

/**
 *
 * @author Willi
 */
public class StartPanel extends JPanel{
    public StartPanel(TicTacToeClient client) {
        JButton startButton = new JButton("Start");
        add(startButton);
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.showPanel(new MainContentPanel(client));
            }
        });
    }
}
