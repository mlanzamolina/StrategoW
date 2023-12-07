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
    // display eliminated characters
    private JButton[] eliminatedHeroesButton = new JButton[40];
    private JButton[] eliminatedVillainsButton = new JButton[40];
    private String cardBackgroundImages = "./src/stratego/images/Who_question_mark.png"; // Array to hold card
                                                                                         // background images
    private String[] originalButtonImages;
    private boolean isHeroTurn = true;
    private ArrayList<Character> heroes = new ArrayList<>();
    private ArrayList<Character> villains = new ArrayList<>();
    private ArrayList<String> heroesOriginalImages = new ArrayList<>();
    private ArrayList<String> villainsOriginalImages = new ArrayList<>();
    private PrintStream printStream;
    private Boolean gameHasInit = false;
    JPanel eliminatedHeroesPanel = new JPanel(new GridLayout(10, 10));
    JPanel eliminatedVillainsPanel = new JPanel(new GridLayout(10, 10));

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
        gameBoardPanel.setPreferredSize(new Dimension(1200, 800));
        // gameBoardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //
        // Add space around the game board

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
        // ...

        // int numHeroes = (int) heroes.stream().filter(hero ->
        // !hero.isAlive()).count();
        // eliminatedHeroesPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,
        // 10));
        // heroes.stream().filter(hero -> !hero.isAlive()).forEach(hero ->
        // eliminatedHeroesPanel.add(new JLabel(new
        // ImageIcon(hero.getImage().getDescription()))));
        // add(eliminatedHeroesPanel, BorderLayout.EAST);

        // int numVillains = (int) villains.stream().filter(villain ->
        // !villain.isAlive()).count();
        // eliminatedVillainsPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,
        // 10));
        // villains.stream().filter(villain -> !villain.isAlive()).forEach(villain ->
        // eliminatedVillainsPanel.add(new JLabel(new
        // ImageIcon(villain.getImage().getDescription()))));
        // add(eliminatedVillainsPanel, BorderLayout.WEST);
        updatePanels();

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
        // String startTurn = !isHeroTurn ? "Heroes" : "Villain";
        resignGame.addActionListener(e -> endGame());
    }

    public void updatePanels() {
        eliminatedHeroesPanel.removeAll(); // Remove all existing components from the panel
        eliminatedHeroesPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        heroes.stream()
                .filter(hero -> !hero.isAlive())
                .forEach(hero -> {
                    int index = hero.getMyHeroCont();
                    if (index < heroesOriginalImages.size()) {
                        eliminatedHeroesPanel.add(new JLabel(new ImageIcon(heroesOriginalImages.get(index))));
                    }
                });
        add(eliminatedHeroesPanel, BorderLayout.EAST);

        eliminatedVillainsPanel.removeAll(); // Remove all existing components from the panel
        eliminatedVillainsPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        villains.stream()
                .filter(villain -> !villain.isAlive())
                .forEach(villain -> {
                    int index = villain.getMyVillainCont();
                    if (index < villainsOriginalImages.size()) {
                        eliminatedVillainsPanel.add(new JLabel(new ImageIcon(villainsOriginalImages.get(index))));
                    }
                });
        add(eliminatedVillainsPanel, BorderLayout.WEST);

        revalidate(); // Revalidate the panels to reflect the changes
        repaint(); // Repaint the panels to reflect the changes
    }

    private void toggleCardVisibility() {
        revalidate();
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
                            // Calculate the position to center the image
                            int x = (getWidth() - image.getIconWidth()) / 2;
                            int y = (getHeight() - image.getIconHeight()) / 2;

                            // Draw the image at the calculated position
                            g.drawImage(image.getImage(), x, y, null);

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

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(50, 50);
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension(50, 50);
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(50, 50);
            }
        };

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
        gameHasInit = true;

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
                    // open a windows for confirm fight with two buttons confirm proceed else
                    // selectedCharacter =null

                    if (selectedCharacter.isHero() != targetCharacter.isHero()) {
                        Object[] options = { "Confirmar", "Cancelar" };
                        int n = JOptionPane.showOptionDialog(this,
                                "¿Estás seguro de que quieres luchar?",
                                "Confirmar lucha",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[1]);

                        if (n == JOptionPane.YES_OPTION) {

                            if (targetCharacter.getName().equals("Tierra")) {
                                // endGame();

                                JOptionPane.showMessageDialog(this, "Villains win!");

                                dispose(); // Close the game window
                                // close other windows of logs and villains heroes
                                // if (heroesWindow != null) {
                                //     heroesWindow.dispose();
                                //     villainsWindow.dispose();
                                // }
                                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                                System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
                                frame.dispose();
                            } else if (targetCharacter.getName().equals("Planet Tierra")) {
                                // endGame();
                                JOptionPane.showMessageDialog(this, "Heroes win! +3 points");

                                dispose(); // Close the game window
                                // close other windows of logs and villains heroes
                                // if (heroesWindow != null) {
                                //     heroesWindow.dispose();
                                //     villainsWindow.dispose();
                                // }
                                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                                System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
                                frame.dispose();
                            } else if ((selectedCharacter.getPowerRating() != 3 &&
                                    (targetCharacter.getName().equals("Nova Blast")
                                            || targetCharacter.getName().equals("Pumpkin Bomb")))) {
                                if ((!targetCharacter.isHero() && isHeroTurn)
                                        || (targetCharacter.isHero() && !isHeroTurn)) {
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
                                    updatePanels();
                                    revalidate();
                                    repaint();
                                    isHeroTurn = !isHeroTurn;
                                    //updateEliminatedCharactersWindows();

                                }
                            } else if (selectedCharacter.getPowerRating() == 2) {
                                // Check if the move is in a straight line horizontally or vertically
                                if (selectedCharacter.getX() == row || selectedCharacter.getY() == col) {
                                    // Check if there is any character between the current position and the target position
                                    int xDirection = Integer.compare(row, selectedCharacter.getX());
                                    int yDirection = Integer.compare(col, selectedCharacter.getY());
                            
                                    int x = selectedCharacter.getX() + xDirection;
                                    int y = selectedCharacter.getY() + yDirection;
                            
                                    while (x != row || y != col) {
                                        Character intermediateCharacter = getCharacterAtLocation(x, y);
                                        if (intermediateCharacter != null) {
                                            // There is a character in the way, so the move is not allowed
                                            System.out.println("Movimiento incorrecto");
                                            return;
                                        }
                            
                                        x += xDirection;
                                        y += yDirection;
                                    }
                            
                                    // Check the character at the target position
                                    targetCharacter = getCharacterAtLocation(row, col);
                                    if (targetCharacter != null) {
                                        if (selectedCharacter.isHero() == targetCharacter.isHero()) {
                                            // The target position is occupied by a character of the same type, so the move is not allowed
                                            System.out.println("Movimiento incorrecto");
                                            return;
                                        } else if (selectedCharacter.getPowerRating() == targetCharacter.getPowerRating()) {
                                            // If they have the same powerRating, they eliminate each other
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
                            
                                            selectedCharacter = null; // Allow another piece to be selected
                                            changeCardBackgrounds();
                                            updatePanels();
                                            revalidate();
                                            repaint();
                                            isHeroTurn = !isHeroTurn;
                                        } else if (selectedCharacter.getPowerRating() > targetCharacter.getPowerRating()) {
                                            // The selected character can eliminate the target character
                                            eliminateCharacter(targetCharacter, false, false);
                                            moveCharacter(row, col);
                                            isHeroTurn = !isHeroTurn;
                                        } else {
                                            // The selected character has a lower power rating, so it is eliminated
                                            eliminateCharacter(selectedCharacter, true, false);
                                            selectedCharacter = null; // Allow another piece to be selected
                                            changeCardBackgrounds();
                                            revalidate();
                                            repaint();
                                            isHeroTurn = !isHeroTurn;
                                        }
                                    }
                                } else {
                                    // The move is not in a straight line, so it is not allowed
                                    System.out.println("Movimiento incorrecto");
                                    return;
                                }
                            } else if (selectedCharacter.getPowerRating() > targetCharacter.getPowerRating() ||
                                    (selectedCharacter.getPowerRating() == 3 &&
                                            (targetCharacter.getName().equals("Nova Blast")
                                                    || targetCharacter.getName().equals("Pumpkin Bomb")))
                                    ||
                                    (selectedCharacter.getPowerRating() == 1
                                            && targetCharacter.getPowerRating() == 10)) {
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
                                updatePanels();
                                revalidate();
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
                                updatePanels();
                                revalidate();
                                repaint();
                                isHeroTurn = !isHeroTurn;
                                //updateEliminatedCharactersWindows();

                            } else if (selectedCharacter.getPowerRating() == 1) {
                                // saveTheEarth();
                            } else {
                                // Handle other cases if needed
                                System.out.println("Movimiento incorrecto");
                            }
                        } else {
                            selectedCharacter = null;
                        }
                    }
                    // changeCardBackgrounds();
                }

            }
        }
    }

    private void endGame() {
        // Handle the game ending logic here
        // System.out.println(isHeroTurn);
        if (isHeroTurn || (!isHeroTurn && gameHasInit)) {
            JOptionPane.showMessageDialog(this, "Heroes win! +3 points");
        } else {
            JOptionPane.showMessageDialog(this, "Villains win!");
        }
        dispose(); // Close the game window
        // close other windows of logs and villains heroes
        // if (heroesWindow != null) {
        //     heroesWindow.dispose();
        //     villainsWindow.dispose();
        // }
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
        frame.dispose();
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
        revalidate();
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
        // if villain change villain character array isAlive
        character.setX(-1);
        character.setY(-1);
        // if (!character.isHero()) {
        // villains.stream().filter(villain ->
        // villain.getName().equals(character.getName())).forEach(villain ->
        // villain.setAlive(false));
        // } else {
        // heroes.stream().filter(hero ->
        // hero.getName().equals(character.getName())).forEach(hero ->
        // hero.setAlive(false));
        // }
        // if (!character.isHero()) {
        // villains.stream()
        // .filter(villain -> villain.getName().equals(character.getName()))
        // .forEach(villain -> {
        // villain.setAlive(false);
        // villain.setX(-1);
        // villain.setY(-1);
        // });
        // } else {
        // heroes.stream()
        // .filter(hero -> hero.getName().equals(character.getName()))
        // .forEach(hero -> {
        // hero.setAlive(false);
        // hero.setX(-1);
        // hero.setY(-1);
        // });
        // }
        // Agrega el personaje a la lista de personajes eliminados
        if (!mutualElim) {
            if (character.isHero()) {
                eliminatedHeroes.add(character);
                heroesScore += 5;
            } else {
                eliminatedVillains.add(character);
                villainsScore += 5;
            }
            // GameBoard();
            updatePanels();
            revalidate();
            repaint();
           // updateEliminatedCharactersWindows();
        }
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

    // private void endGame() {
    // // Handle the game ending logic here
    // if (isHeroTurn) {
    // JOptionPane.showMessageDialog(this, "Villains win!");
    // // Display a message
    // } else {
    // // Display a message
    // JOptionPane.showMessageDialog(this, "Heroes win! +3 points");
    // }
    // dispose(); // Close the game window
    // // close other windows of logs and villains heroes
    // heroesWindow.dispose();
    // villainsWindow.dispose();
    // frame.dispose();
    // System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    // System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
    // }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameBoard());
    }
}