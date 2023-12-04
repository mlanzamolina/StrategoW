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
private Character[] characters;

public GameBoard() {
    // Set up the main frame
    setTitle("Stratego - Marvel Heroes");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(10, 10));

    characters = InitCharacters.getInstance().getCharacters();
    // Create the game board
    for (int row = 0; row < 10; row++) {
        for (int col = 0; col < 10; col++) {
            JButton button = createGameSpace(row, col);
            add(button);
        }
    }
    // Initialize characters directly in the constructor
    int cont=0;
    JFrame imageFrame = new JFrame("Character Images");
    imageFrame.setLayout(new FlowLayout());
    for (Character character : characters) {
        System.out.println("Found character: " + character.getName()+"\tid:" + cont+"\tx:"+character.getX()+"\ty:"+character.getY());
        ImageIcon image = character.getImage();
        if (image != null) {
            JLabel imageLabel = new JLabel(image);
            imageFrame.add(imageLabel);
        }
        cont++;
    }
    imageFrame.pack();
    imageFrame.setVisible(true);

    // Set up the frame size
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
}

private JButton createGameSpace(int row, int col) {
    JButton button = new JButton() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Character character : characters) {
                if (character.getX() == row && character.getY() == col) {
                    ImageIcon image = character.getImage();
                    if (image != null) {
                        g.drawImage(image.getImage(), 0, 0, null);
                        int width = (int) (image.getIconWidth());
                        int height = (int) (image.getIconHeight());
                        setPreferredSize(new Dimension(width, height));
                    }
                }
            }
        }
    };
    button.setPreferredSize(new Dimension(75, 75));

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

        for (Character character : characters) {
            if (character.getX() == row && character.getY() == col) {
                System.out.println("Found character: " + character.getName());
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameBoard());
    }
}
