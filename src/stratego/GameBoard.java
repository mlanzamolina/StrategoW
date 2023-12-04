package stratego;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class GameBoard extends JFrame {
    private Character[] characters;
    private JButton[][] buttons = new JButton[10][10]; // Matriz de botones

    public GameBoard() {
        // Set up the main frame
        setTitle("Stratego - Marvel Heroes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 10));

        characters = InitCharacters.getInstance().getCharacters();
        // Create the game board
        // Crear el tablero de juego
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                buttons[row][col] = createGameSpace(row, col);
                add(buttons[row][col]);
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.black, 1));
            }
        }
        // En tu clase principal o en el método de inicialización de la interfaz gráfica
        JFrame frame = new JFrame("Game Logs");
        JTextArea textArea = new JTextArea(24, 80);
        textArea.setEditable(false); // Hacer que el área de texto no sea editable
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);

        // Redirige la salida estándar a la JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
        // Initialize characters directly in the constructor
        // int cont=0;
        // JFrame imageFrame = new JFrame("Character Images");
        // imageFrame.setLayout(new FlowLayout());
        // for (Character character : characters) {
        // System.out.println("Found character: " + character.getName()+"\tid:" +
        // cont+"\tx:"+character.getX()+"\ty:"+character.getY());
        // ImageIcon image = character.getImage();
        // if (image != null) {
        // JLabel imageLabel = new JLabel(image);
        // imageFrame.add(imageLabel);
        // }
        // cont++;
        // }
        // imageFrame.pack();
        // imageFrame.setVisible(true);

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
                    if (character.isAlive() && character.getX() == row && character.getY() == col) {
                        ImageIcon image = character.getImage();
                        if (image != null) {
                            g.drawImage(image.getImage(), 0, 0, null);
                            int width = (int) (image.getIconWidth());
                            int height = (int) (image.getIconHeight());
                            setPreferredSize(new Dimension(width, height));

                            // Pinta el borde de azul si es un héroe, de rojo si no lo es
                            if (character.isHero()) {
                                setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
                            } else {
                                setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            }

                            // Si el personaje es el seleccionado, pinta el borde de verde
                            if (character == selectedCharacter) {
                                setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                            }
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

        // Add action listener for button clicks (you can customize this based on your
        // needs)
        button.addActionListener(e -> handleButtonClick(row, col));

        return button;
    }

    private Character selectedCharacter = null;
    private boolean isHeroTurn = true;

    private void handleButtonClick(int row, int col) {
        System.out.println("Clicked on row " + row + ", column " + col);

        // Resto del código...

        if (selectedCharacter != null) {
            handleCharacterMove(row, col);
        } else {
            handleCharacterSelection(row, col);
        }
    }

    private void handleCharacterMove(int row, int col) {
        if (selectedCharacter.getX() == row && selectedCharacter.getY() == col) {
            handleCharacterDeselection(row, col);
        } else {
            // Resto del código...
            boolean isEmpty = isCellEmpty(row, col);

            boolean isAdjacent = selectedCharacter.getPowerRating() == 2 ||
                    (selectedCharacter.getX() == row && Math.abs(selectedCharacter.getY() - col) == 1) ||
                    (selectedCharacter.getY() == col && Math.abs(selectedCharacter.getX() - row) == 1);

            if (isEmpty && isAdjacent) {
                // Check if there's a character at the new location
                Character targetCharacter = getCharacterAtLocation(row, col);

                moveCharacter(row, col);

                // Resto del código...
                isHeroTurn = !isHeroTurn;
            } else if (isAdjacent) {
                // Check if there's a character at the new location
                Character targetCharacter = getCharacterAtLocation(row, col);

                if (targetCharacter != null) {
                    // Elimination logic
                    if (selectedCharacter.getPowerRating() > targetCharacter.getPowerRating()) {
                        if ((!targetCharacter.isHero() && isHeroTurn) || (targetCharacter.isHero() && !isHeroTurn)) {
                            eliminateCharacter(targetCharacter);

                            moveCharacter(row, col);

                            // Resto del código...
                            isHeroTurn = !isHeroTurn;

                        }
                    } else {
                        // Handle other cases if needed
                    }
                }

            }
        }
    }

    private void handleCharacterSelection(int row, int col) {
        for (Character character : characters) {
            if (character.getX() == row && character.getY() == col && character.getMoveable()) {
                if ((character.isHero() && isHeroTurn) || (!character.isHero() && !isHeroTurn)) {
                    selectCharacter(row, col, character);
                    break;
                }
            }
        }
    }

    private void handleCharacterDeselection(int row, int col) {
        // Restaura el color del borde dependiendo de si el personaje es un héroe o no
        buttons[row][col]
                .setBorder(BorderFactory.createLineBorder(selectedCharacter.isHero() ? Color.BLUE : Color.RED, 1));

        selectedCharacter = null;
        // isHeroTurn = !isHeroTurn;
    }

    private boolean isCellEmpty(int row, int col) {
        for (Character character : characters) {
            if (character.getX() == row && character.getY() == col) {
                return false;
            }
        }
        return true;
    }

    private void moveCharacter(int row, int col) {
        int oldX = selectedCharacter.getX();
        int oldY = selectedCharacter.getY();

        selectedCharacter.setX(row);
        selectedCharacter.setY(col);

        // Restaura el color del borde dependiendo de si el personaje es un héroe o no
        buttons[row][col]
                .setBorder(BorderFactory.createLineBorder(selectedCharacter.isHero() ? Color.BLUE : Color.RED, 1));

        buttons[oldX][oldY].setBorder(BorderFactory.createLineBorder(Color.black, 1));

        selectedCharacter = null;

        repaint();
    }

    private void selectCharacter(int row, int col, Character character) {
        selectedCharacter = character;
        System.out.println("Found character: " + character.getName());

        // Pinta el borde de verde cuando el personaje es seleccionado
        buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
    }

    private Character getCharacterAtLocation(int row, int col) {
        for (Character character : characters) {
            if (character.getX() == row && character.getY() == col) {
                return character;
            }
        }
        return null;
    }

    private void eliminateCharacter(Character character) {
        buttons[selectedCharacter.getX()][selectedCharacter.getY()]
                .setBorder(BorderFactory.createLineBorder(Color.black, 1));
        character.setAlive(false);
        character.setX(-1);
        character.setY(-1);
        System.out.println("Character " + character.getName() + " has been eliminated by "
                + selectedCharacter.getName());

        // Redibuja la interfaz gráfica
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameBoard());
    }
}