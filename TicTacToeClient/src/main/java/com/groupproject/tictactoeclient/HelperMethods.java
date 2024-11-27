/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.groupproject.tictactoeclient;

import java.util.ArrayList;
import java.util.Arrays;


/**
 *
 * @author William
 */
public class HelperMethods {
    private TicTacToeClient client;
    
    public HelperMethods(TicTacToeClient client) {
        this.client = client;
    }
    
    public int[][] GetBoard(int gid) {
        System.out.println("Fetching board...");
        String response = client.proxy.getBoard(gid);
        System.out.println(response);
        
        if ("ERROR-NOMOVES".equals(response)) {
            return null;
        }
        
        // Split by lines and map each line to an integer array
        return Arrays.stream(response.split("\n"))
                     .map(row -> Arrays.stream(row.split(","))
                                       .mapToInt(Integer::parseInt)
                                       .toArray())
                     .toArray(int[][]::new);
    }
    
    public int[][] GetBoard() {
        if (client.GID == null) {
            return null;
        }
        
        return GetBoard(Integer.parseInt(client.GID));
    }
    
    public boolean IsHost() {
        return client.UID.equals(client.HOST_UID);
    }
    
    
    /**
     * Take the square with selected index
     * @param index square to take
     * @return return true if square was taken, false if it was already taken by opponent
     */
    public boolean TakeSqure(int index) {
        if ("0".equals(client.proxy.checkSquare(index % 3, (int) Math.floor(index / 3), Integer.parseInt(client.GID)))) {
            System.out.println("Taking square");
            client.proxy.takeSquare(index % 3, (int) Math.floor(index / 3), Integer.parseInt(client.GID), Integer.parseInt(client.UID));
            String winID = client.proxy.checkWin(Integer.parseInt(client.GID));
            if (Integer.parseInt(winID) == 1){
                System.out.println("Player 1 WINS");
                client.proxy.setGameState(Integer.parseInt(client.GID), 1);
            }
            if (Integer.parseInt(winID) == 2){
                System.out.println("Player 2 WINS");
                client.proxy.setGameState(Integer.parseInt(client.GID), 2);
                

            }
            if (Integer.parseInt(winID) == 3){
                System.out.println("DRAW");
                client.proxy.setGameState(Integer.parseInt(client.GID), 3);
                

            }
            return true;
        } else {
            System.out.println("Cannot take square");
            return false;
        }
    }
}
