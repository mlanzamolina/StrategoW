/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stratego;

/**
 *
 * @author marco
 */
import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {

    public GameBoard() {
        // Set up the main frame
        setTitle("Stratego - Marvel Heroes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 10));

        // Create the game board
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = createGameSpace(row, col);
                add(button);
            }
        }

        // Set up the frame size
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

 private JButton createGameSpace(int row, int col) {
    JButton button = new JButton();
    // Customize button appearance based on your requirements
    button.setPreferredSize(new Dimension(50, 50));

    // Check if the space is within the forbidden zones
    boolean isYellowZone = (row >= 4 && row <= 5 && col >= 2 && col <= 3);
    boolean isMagentaZone = (row >= 4 && row <= 5 && col >= 6 && col <= 7);

    if (isYellowZone || isMagentaZone) {
        // Set color for forbidden zones (yellow and magenta)
        button.setBackground(isYellowZone ? Color.YELLOW : Color.MAGENTA);
        button.setEnabled(false); // Disable buttons in forbidden zones
    }

    // Add action listener for button clicks (you can customize this based on your needs)
    button.addActionListener(e -> handleButtonClick(row, col));

    return button;
}

    private void handleButtonClick(int row, int col) {
        // Handle button click based on the row and column
        System.out.println("Clicked on row " + row + ", column " + col);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameBoard());
    }
}
