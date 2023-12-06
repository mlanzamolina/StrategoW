package stratego;

import javax.swing.*;
import java.awt.*;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JFrame {
    private Character[] characters;
    private JButton[][] buttons = new JButton[10][10]; // Matriz de botones
    // private JButton confirmEndTurnHideCards = new JButton("Confirm End Turn");
    private JButton resignGame = new JButton("Resign Game");
    private String cardBackgroundImages = "./src/stratego/images/Who_question_mark.png"; // Array to hold card
                                                                                         // background images
    private String[] originalButtonImages;
    private boolean isHeroTurn = true;
    private ArrayList<Character> heroes = new ArrayList<>();
    private ArrayList<Character> villains = new ArrayList<>();
    private ArrayList<String> heroesOriginalImages = new ArrayList<>();
    private ArrayList<String> villainsOriginalImages = new ArrayList<>();
    private PrintStream printStream;

    List<Character> eliminatedHeroes = new ArrayList<>();
    List<Character> eliminatedVillains = new ArrayList<>();
    JFrame frame = new JFrame("Game Logs");
    private int heroesScore = 0;
    private int villainsScore = 0;

    private void loadOriginalButtonImages() {
        originalButtonImages = new String[characters.length];
        int contCharacter = 0;
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].isHero()) {
                heroes.add(characters[i]);
                heroesOriginalImages.add(characters[i].getImage().getDescription());
            } else {
                villains.add(characters[i]);
                villainsOriginalImages.add(characters[i].getImage().getDescription());
            }
            // originalButtonImages[contCharacter] =
            // characters[contCharacter].getImage().getDescription();
            contCharacter++;
        }
    }

    public GameBoard() {
        // Set up the main frame
        setTitle("Stratego - Marvel Heroes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Use BorderLayout for main layout

        // Panel for game board
        JPanel gameBoardPanel = new JPanel(new GridLayout(10, 10));
        gameBoardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add space around the game board

        // Initialize the game board buttons
        characters = InitCharacters.getInstance().getCharacters();
        loadOriginalButtonImages();
        // confirmEndTurnHideCards.addActionListener(e -> toggleCardVisibility());
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                buttons[row][col] = createGameSpace(row, col);
                gameBoardPanel.add(buttons[row][col]);
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.black, 1));
            }
        }

        // Add the game board panel to the center
        add(gameBoardPanel, BorderLayout.CENTER);
        // Add action listener to resignGame button

        // Styling and adding buttons
        // styleButton(confirmEndTurnHideCards, Color.GREEN, new Font("Arial",
        // Font.BOLD, 14));
        styleButton(resignGame, Color.RED, new Font("Arial", Font.BOLD, 14));

        // Add buttons to the frame
        // add(confirmEndTurnHideCards, BorderLayout.SOUTH);
        add(resignGame, BorderLayout.NORTH);
        // En tu clase principal o en el método de inicialización de la interfaz gráfica
        //JFrame frame = new JFrame("Game Logs");
        JTextArea textArea = new JTextArea(24, 80);
        textArea.setEditable(false); // Hacer que el área de texto no sea editable
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);

        // Redirige la salida estándar a la JTextArea
        printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        resignGame.addActionListener(e -> endGame());
    }

    private void toggleCardVisibility() {
        repaint();
        isHeroTurn = !isHeroTurn;
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
            boolean isEmpty = isCellEmpty(row, col);

            boolean isAdjacent = (selectedCharacter.getX() == row && Math.abs(selectedCharacter.getY() - col) == 1) ||
                    (selectedCharacter.getY() == col && Math.abs(selectedCharacter.getX() - row) == 1);

            boolean canMove = isAdjacent;

            if (selectedCharacter.getPowerRating() == 2 && !isAdjacent) {
                canMove = (selectedCharacter.getX() == row || selectedCharacter.getY() == col)
                        && isPathClear(selectedCharacter.getX(), selectedCharacter.getY(), row, col);
            }

            if (isEmpty && canMove) {
                Character targetCharacter = getCharacterAtLocation(row, col);

                moveCharacter(row, col);

                isHeroTurn = !isHeroTurn;
            } else if (isAdjacent || selectedCharacter.getPowerRating() == 2) {
                Character targetCharacter = getCharacterAtLocation(row, col);

                if (targetCharacter != null) {
                    // Asegúrate de que los personajes son de equipos opuestos
                    if (selectedCharacter.isHero() != targetCharacter.isHero()) {
                        if (selectedCharacter.getPowerRating() == 2) {
                            Character adjacentCharacter = getCharacterAtLocation(row - 1, col);
                            if (adjacentCharacter != null && (selectedCharacter.isHero() != adjacentCharacter.isHero()
                                    || selectedCharacter.isHero() == adjacentCharacter.isHero())) {
                                // No permitas el movimiento
                                System.out.println("No puedes moverte allí");
                                return;
                            }
                        }
                        if (targetCharacter.getName().equals("Tierra")) {
                            endGame("Villains");
                        } else if (targetCharacter.getName().equals("Planet Tierra")) {
                            endGame("Heroes");
                        } else if (selectedCharacter.getPowerRating() > targetCharacter.getPowerRating() ||
                                (selectedCharacter.getPowerRating() == 3 &&
                                        (targetCharacter.getName().equals("Nova Blast")
                                                || targetCharacter.getName().equals("Pumpkin Bomb")))
                                ||
                                (selectedCharacter.getPowerRating() == 1 && targetCharacter.getPowerRating() == 10)) {
                            if ((!targetCharacter.isHero() && isHeroTurn)
                                    || (targetCharacter.isHero() && !isHeroTurn)) {
                                eliminateCharacter(targetCharacter, false, false);
                                moveCharacter(row, col);
                                isHeroTurn = !isHeroTurn;
                            }
                        } else if (selectedCharacter.getPowerRating() < targetCharacter.getPowerRating()) {
                            // Si una pieza menor ataca a una mayor, se elimina sola
                            eliminateCharacter(selectedCharacter, true, false);
                            selectedCharacter = null; // Permitir la selección de otra pieza
                            changeCardBackgrounds();
                            repaint();
                            isHeroTurn = !isHeroTurn;
                        } else if (selectedCharacter.getPowerRating() == targetCharacter.getPowerRating()) {
                            // Si tienen el mismo powerRating, se eliminan solas
                            List<Character> charactersToEliminate = new ArrayList<>();
                            charactersToEliminate.add(targetCharacter);
                            buttons[targetCharacter.getX()][targetCharacter.getY()]
                                    .setBorder(BorderFactory.createLineBorder(Color.black, 1));
                            charactersToEliminate.add(selectedCharacter);
                            buttons[selectedCharacter.getX()][selectedCharacter.getY()]
                                    .setBorder(BorderFactory.createLineBorder(Color.black, 1));
                            for (Character character : charactersToEliminate) {
                                eliminateCharacter(character, false, true);
                            }

                            selectedCharacter = null; // Permitir la selección de otra pieza
                            changeCardBackgrounds();
                            repaint();
                            isHeroTurn = !isHeroTurn;
                            updateEliminatedCharactersWindows();
                        } else if (selectedCharacter.getPowerRating() == 1) {
                            // saveTheEarth();
                        } else {
                            // Handle other cases if needed
                        }
                    }
                    // changeCardBackgrounds();
                }

            }
        }
    }

    private void endGame(String winner) {
        // Handle the game ending logic here
        if (isHeroTurn) {
            JOptionPane.showMessageDialog(this, "Heroes win! +3 points"); // Display a message
        } else {
            JOptionPane.showMessageDialog(this, "Villains win!"); // Display a message
        }
        dispose(); // Close the game window
    }

    private boolean isPathClear(int startX, int startY, int endX, int endY) {
        int xDirection = startX == endX ? 0 : (endX - startX) / Math.abs(endX - startX);
        int yDirection = startY == endY ? 0 : (endY - startY) / Math.abs(endY - startY);

        for (int i = 1; i < Math.max(Math.abs(endX - startX), Math.abs(endY - startY)); i++) {
            if (!isCellEmpty(startX + i * xDirection, startY + i * yDirection)) {
                return false;
            }
        }

        return true;
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
        changeCardBackgrounds();
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

    private void eliminateCharacter(Character character, boolean deathByBomb, boolean mutualElim) {

        buttons[selectedCharacter.getX()][selectedCharacter.getY()]
                .setBorder(BorderFactory.createLineBorder(Color.black, 1));
        character.setAlive(false);
        character.setX(-1);
        character.setY(-1);
        if (deathByBomb) {
            System.out.println("Character " + character.getName() + " Eliminated himself! ");
        } else {
            System.out.println(
                    "Character " + character.getName() + " has been eliminated by " + selectedCharacter.getName());
        }
        // Agrega el personaje a la lista de personajes eliminados
        if (!mutualElim) {

            if (character.isHero()) {
                eliminatedHeroes.add(character);
                heroesScore += 5;
            } else {
                eliminatedVillains.add(character);
                villainsScore += 5;
            }
            repaint();
            updateEliminatedCharactersWindows();
        }

        // Actualiza las ventanas de personajes eliminados

        // Redibuja la interfaz gráfica
    }

    private JFrame heroesWindow;
    private JFrame villainsWindow;

    private void updateEliminatedCharactersWindows() {
        // Crea y configura las ventanas si aún no existen

        if (heroesWindow == null) {
            heroesWindow = new JFrame("Villanos");
            heroesWindow.setSize(300, 200);
            heroesWindow.setLocationRelativeTo(null);
            heroesWindow.getContentPane().setLayout(new BorderLayout());
            heroesWindow.setVisible(true);
        }
        if (villainsWindow == null) {
            villainsWindow = new JFrame("Héroes");
            villainsWindow.setSize(300, 200);
            villainsWindow.setLocationRelativeTo(null);
            villainsWindow.getContentPane().setLayout(new BorderLayout());
            villainsWindow.setVisible(true);
        }

        // Crea los paneles para los personajes eliminados
        JPanel heroesPanel = new JPanel();
        heroesPanel.setLayout(new BoxLayout(heroesPanel, BoxLayout.Y_AXIS));
        JPanel villainsPanel = new JPanel();
        villainsPanel.setLayout(new BoxLayout(villainsPanel, BoxLayout.Y_AXIS));

        // Agrega los personajes eliminados a los paneles
        for (Character hero : eliminatedHeroes) {
            heroesPanel.add(new JLabel(hero.getName() + " (HERO) Power: " + hero.getPowerRating()));
        }
        for (Character villain : eliminatedVillains) {
            villainsPanel.add(new JLabel(villain.getName() + " (VILLAIN) Power: " + villain.getPowerRating()));
        }

        // Crea los JScrollPane y añade los paneles a ellos
        JScrollPane heroesScrollPane = new JScrollPane(heroesPanel);
        JScrollPane villainsScrollPane = new JScrollPane(villainsPanel);

        // Actualiza el contenido de las ventanas
        heroesWindow.getContentPane().removeAll();
        villainsWindow.getContentPane().removeAll();
        heroesWindow.getContentPane().add(new JLabel("Puntuación: " + heroesScore), BorderLayout.NORTH);
        heroesWindow.getContentPane().add(heroesScrollPane, BorderLayout.CENTER);
        villainsWindow.getContentPane().add(new JLabel("Puntuación: " + villainsScore), BorderLayout.NORTH);
        villainsWindow.getContentPane().add(villainsScrollPane, BorderLayout.CENTER);
        heroesWindow.getContentPane().add(new JLabel("Puntuación: " + heroesScore), BorderLayout.NORTH);
        villainsWindow.getContentPane().add(new JLabel("Puntuación: " + villainsScore), BorderLayout.NORTH);

        // Actualiza las ventanas
        heroesWindow.revalidate();
        heroesWindow.repaint();
        villainsWindow.revalidate();
        villainsWindow.repaint();
    }

    private void styleButton(JButton button, Color color, Font font) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    private void changeCardBackgrounds() {
        int contCharacterVillain = 0;
        int contCharacterHero = 0;
        if (isHeroTurn) {
            for (Character character : heroes) {
                character.setImage(cardBackgroundImages);
            }
            // reset to default values
            for (Character character : villains) {
                character.setImage(villainsOriginalImages.get(contCharacterVillain));
                contCharacterVillain++;
            }

        }
        if (!isHeroTurn) {
            for (Character character : villains) {
                character.setImage(cardBackgroundImages);
            }
            // reset to default values
            for (Character character : heroes) {
                character.setImage(heroesOriginalImages.get(contCharacterHero));
                contCharacterHero++;
            }
        }
    }

    private void endGame() {
        // Handle the game ending logic here
        if (isHeroTurn) {
            JOptionPane.showMessageDialog(this, "Heroes win! +3 points"); // Display a message
        } else {
            JOptionPane.showMessageDialog(this, "Villains win!"); // Display a message
        }
        dispose(); // Close the game window
        // close other windows of logs and villains heroes
        heroesWindow.dispose();
        villainsWindow.dispose();
        frame.dispose();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameBoard());
    }
}