/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.groupproject.tictactoeclient;
import com.formdev.flatlaf.FlatDarkLaf;
import com.tttws.TicTacToeWS;
import com.tttws.TicTacToeWebService;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Willi
 */
public class TicTacToeClient {
    private static TicTacToeWebService service;
    private static TicTacToeWS proxy;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
        }
    }
}