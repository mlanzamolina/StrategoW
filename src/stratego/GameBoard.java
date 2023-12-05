package stratego;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class GameBoard extends JFrame {
    private Character[] characters;
    private JButton[][] buttons = new JButton[10][10]; // Matriz de botones
    List<Character> eliminatedHeroes = new ArrayList<>();
    List<Character> eliminatedVillains = new ArrayList<>();
    private int heroesScore = 0;
    private int villainsScore = 0;

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
                            isHeroTurn = !isHeroTurn;
                            repaint();
                            updateEliminatedCharactersWindows();
                        } else if (selectedCharacter.getPowerRating() == 1) {
                            // saveTheEarth();
                        } else {
                            // Handle other cases if needed
                        }
                    }
                }
            }
        }
    }

    private void endGame(String winner) {
        // Muestra un mensaje al usuario
        if (winner.equals("Villains")) {
            System.out.println("¡Los villanos han capturado la Tierra y ganado el juego!");
        } else if (winner.equals("Heroes")) {
            System.out.println("¡Los héroes han capturado el Planeta Tierra y ganado el juego!");
        }

        // Aquí puedes poner la lógica para terminar el juego
        // Por ejemplo, podrías detener todos los hilos de ejecución del juego, cerrar
        // todas las ventanas, etc.
        System.out.println("El juego ha terminado.");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameBoard());
    }
}