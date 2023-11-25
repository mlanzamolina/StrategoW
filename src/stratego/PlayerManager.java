/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stratego;

/**
 *
 * @author marco
 */
import java.util.ArrayList;

public class PlayerManager {
    public static ArrayList<Player> players = new ArrayList<>();

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void printPlayers() {
        System.out.println("Current Players:");
        for (Player player : players) {
            System.out.println("Username: " + player.getUsername());
            System.out.println("Password: " + player.getPassword());
            System.out.println("Puntos: " + player.getPuntos());
            System.out.println("ContHero: " + player.getContHero());
            System.out.println("ContVillain: " + player.getContVillain());
            System.out.println("------------------------");
        }
    }

    // Other methods as needed...
}

