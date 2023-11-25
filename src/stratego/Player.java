/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stratego;

/**
 *
 * @author marco
 */
import java.util.Scanner;

public class Player {
    // Fields
    private String username;
    private String password;
    private int puntos;
    private int contHero;
    private int contVillain;

    // Constructor
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.puntos = 0;
        this.contHero = 0;
        this.contVillain = 0;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getContHero() {
        return contHero;
    }

    public void setContHero(int contHero) {
        this.contHero = contHero;
    }

    public int getContVillain() {
        return contVillain;
    }

    public void setContVillain(int contVillain) {
        this.contVillain = contVillain;
    }

    // Method to create a player with validation
    public static Player createPlayer() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            // Validate uniqueness of username
            if (!isUsernameUnique(username)) {
                System.out.println("Username is not unique. Please choose another one.");
                continue;
            }

            System.out.print("Enter password (5 characters): ");
            String password = scanner.nextLine();

            // Validate password length
            if (password.length() != 5) {
                System.out.println("Password must be exactly 5 characters long. Please try again.");
                continue;
            }

            // Player creation successful
            return new Player(username, password);
        }
    }

    // Helper method to check uniqueness of username
    private static boolean isUsernameUnique(String username) {
        // Logic to check if the username is unique
        // You may implement this based on your storage or database logic
        // For now, let's assume all usernames are unique
        return true;
    }

    // Other methods as needed...
}
