/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stratego;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class InitCharacters {
    private static InitCharacters instance = null;
    private Character[] characters;

    private InitCharacters() {
        characters = makeCharacters();
    }

    public static InitCharacters getInstance() {
        if (instance == null) {
            instance = new InitCharacters();
        }
        return instance;
    }

    public Character[] getCharacters() {
        return characters;
    }

    private Character[] makeCharacters() {
        // Create an array of Character objects
        Character[] spaceForCharacters = new Character[80];
        Character[] characters = populateCharacterArray(spaceForCharacters);

        List<String> availableHeroCoordinates = new ArrayList<>();
        List<String> availableHeroPlanetCoordinates = new ArrayList<>();
        List<String> availableBombHeroPlanetCoordinates = new ArrayList<>();
        List<String> availableBombHeroPR2Coordinates = new ArrayList<>();

        List<String> availableVillainCoordinates = new ArrayList<>();
        List<String> availableVillainPlanetCoordinates = new ArrayList<>();
        List<String> availableBombVillainPlanetCoordinates = new ArrayList<>();
        List<String> availableBombVillainPR2Coordinates = new ArrayList<>();

        // Initialize the lists of available coordinates excluding rows 4 and 5
        int contHero = 0;
        int contVillain = 0;
        int contHeroPlanet = 0;
        int contVillainPlanet = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i != 4 && i != 5) {
                    if (i >= 6) {
                        availableHeroCoordinates.add(i + "-" + j);
                        contHero++;
                        if (i == 9) {
                            if (j >= 1 && j <= 8) {
                                availableHeroPlanetCoordinates.add(i + "-" + j);
                                contHeroPlanet++;
                            }
                        }
                        if (i == 9 || i == 8) {
                            availableBombHeroPlanetCoordinates.add(i + "-" + j);
                        }
                        if (i == 6 || i == 7) {
                            availableBombHeroPR2Coordinates.add(i + "-" + j);
                        }

                    } else {
                        availableVillainCoordinates.add(i + "-" + j);
                        contVillain++;
                        if (i == 0) {
                            if (j >= 1 && j <= 8) {
                                availableVillainPlanetCoordinates.add(i + "-" + j);
                                contVillainPlanet++;
                            }
                        }
                        if (i == 0 || i == 1) {
                            availableBombVillainPlanetCoordinates.add(i + "-" + j);
                        }
                        if (i == 2 || i == 3) {
                            availableBombVillainPR2Coordinates.add(i + "-" + j);
                        }
                    }
                }
            }
        }

        // Shuffle the lists of available coordinates for heroes and villains
        Collections.shuffle(availableHeroCoordinates);
        Collections.shuffle(availableVillainCoordinates);
        Collections.shuffle(availableHeroPlanetCoordinates);
        Collections.shuffle(availableVillainPlanetCoordinates);
        Collections.shuffle(availableBombHeroPlanetCoordinates);
        Collections.shuffle(availableBombVillainPlanetCoordinates);
        Collections.shuffle(availableBombHeroPR2Coordinates);
        Collections.shuffle(availableBombVillainPR2Coordinates);
        List<String> availablePumpkinBomb = new ArrayList<>();
        List<String> availableNovaBlastBomb = new ArrayList<>();

        int totalCharacters = characters.length;

        int contHeroFound = 0;
        int contVillainFound = 0;
        String earthHero = availableHeroPlanetCoordinates.get(0);
        availableBombHeroPlanetCoordinates.remove(availableHeroPlanetCoordinates.get(0));
        String earthVillain = availableVillainPlanetCoordinates.get(0);
        availableBombVillainPlanetCoordinates.remove(availableVillainPlanetCoordinates.get(0));
        assignCoordinates(characters[0], earthHero);
        characters[0].setMoveable(false);

        assignCoordinates(characters[1], earthVillain);
        characters[1].setMoveable(false);
        availableHeroCoordinates.remove(availableHeroPlanetCoordinates.get(0));
        availableVillainCoordinates.remove(availableVillainPlanetCoordinates.get(0));

        Set<String> coordinatesSetNovaBlast = generateRandomCoordinates(characters[0].getX(), characters[0].getY());
        availableNovaBlastBomb = new ArrayList<>(coordinatesSetNovaBlast);
        Set<String> coordinatesSetPumpkinBomb = generateRandomCoordinates(characters[1].getX(), characters[1].getY());
        availablePumpkinBomb = new ArrayList<>(coordinatesSetPumpkinBomb);
        // Print the generated coordinates
        System.out.println("Generated Coordinates NOVA BLAST:");
        int contNovaBlast = 8;
        int indexNovaBlast = 0;
        int contPumpkinBomb = 2;
        int indexPumpkinBomb = 0;
        for (String coordinate : coordinatesSetNovaBlast) {
            // split x and y
            String[] coordinatesArray = coordinate.split("-");
            availableHeroCoordinates.remove(availableNovaBlastBomb.get(indexNovaBlast));
            availableBombHeroPlanetCoordinates.remove(coordinate);
            int x = Integer.parseInt(coordinatesArray[0]);
            int y = Integer.parseInt(coordinatesArray[1]);
            // System.out.println("this is x:"+x+"this is y"+y);
            characters[contNovaBlast].setX(x);
            characters[contNovaBlast].setY(y);
            characters[contNovaBlast].setMoveable(false);
            contNovaBlast++;
            indexNovaBlast++;
        }
        // place remaining Nova Blast
        int remainingNovaBlast = 6 - indexNovaBlast;
        for (int i = 0; i < remainingNovaBlast; i++) {
            String coordinate = availableBombHeroPlanetCoordinates.get(i);
            availableHeroCoordinates.remove(availableBombHeroPlanetCoordinates.get(i));
            assignCoordinates(characters[contNovaBlast], coordinate);
            characters[contNovaBlast].setMoveable(false);
            contNovaBlast++;
            // contHeroFound++;
        }

        System.out.println("Generated Coordinates PUMPKINBONB:");
        for (String coordinate : coordinatesSetPumpkinBomb) {
            // split x and y
            String[] coordinatesArray = coordinate.split("-");
            availableVillainCoordinates.remove(coordinate);
            availableBombVillainPlanetCoordinates.remove(coordinate);
            int x = Integer.parseInt(coordinatesArray[0]);
            int y = Integer.parseInt(coordinatesArray[1]);
            // System.out.println("this is x:"+x+"this is y"+y);
            characters[contPumpkinBomb].setX(x);
            characters[contPumpkinBomb].setY(y);
            characters[contPumpkinBomb].setMoveable(false);
            contPumpkinBomb++;
            indexPumpkinBomb++;
        }
        // place remaining Pumpkin Bomb
        int remainingPumpkinBomb = 6 - indexPumpkinBomb;
        for (int i = 0; i < remainingPumpkinBomb; i++) {
            String coordinate = availableBombVillainPlanetCoordinates.get(i);
            availableVillainCoordinates.remove(availableBombVillainPlanetCoordinates.get(i));
            assignCoordinates(characters[contPumpkinBomb], coordinate);
            characters[contPumpkinBomb].setMoveable(false);
            contPumpkinBomb++;
            // contVillainFound++;
        }
        System.out.println("Placing powerranking 2 villains");
        // place villains PR 2
        int pr2foundVillain = 0;
        int pr2foundHero = 0;
        for (int i = 0; i < totalCharacters; i++) {
            if (!characters[i].isHero() && characters[i].getPowerRating() == 2) {
                String coordinate = availableBombVillainPR2Coordinates.get(pr2foundVillain);
                availableVillainCoordinates.remove(availableBombVillainPR2Coordinates.get(pr2foundVillain));
                assignCoordinates(characters[i], coordinate);
                pr2foundVillain++;
            }
        }
        System.out.println("Placing powerranking 2 heroes");
        // place heroes PR 2
        for (int i = 0; i < totalCharacters; i++) {
            if (characters[i].isHero() && characters[i].getPowerRating() == 2) {
                String coordinate = availableBombHeroPR2Coordinates.get(pr2foundHero);
                availableHeroCoordinates.remove(availableBombHeroPR2Coordinates.get(pr2foundHero));
                assignCoordinates(characters[i], coordinate);
                pr2foundHero++;
            }
        }

        for (int i = 0; i < totalCharacters; i++) {
            if (characters[i].isHero() && !characters[i].getName().equals("Tierra")
                    && !characters[i].getName().equals("Nova Blast") && characters[i].getPowerRating() != 2) {
                String coordinate = availableHeroCoordinates.get(contHeroFound);
                assignCoordinates(characters[i], coordinate);
                contHeroFound++;
            }
        }

        for (int i = 0; i < totalCharacters; i++) {
            if (!characters[i].isHero() && !characters[i].getName().equals("Planet Tierra")
                    && !characters[i].getName().equals("Pumpkin Bomb") && characters[i].getPowerRating() != 2) {
                String coordinate = availableVillainCoordinates.get(contVillainFound);
                assignCoordinates(characters[i], coordinate);
                contVillainFound++;
            }
        }

        return characters;
    }

    private static void assignCoordinates(Character character, String coordinate) {
        String[] coordinatesArray = coordinate.split("-");
        int x = Integer.parseInt(coordinatesArray[0]);
        int y = Integer.parseInt(coordinatesArray[1]);

        character.setX(x);
        character.setY(y);
    }

    private static Set<String> generateRandomCoordinates(int centerX, int centerY) {
        Set<String> coordinatesSet = new HashSet<>();
        Random random = new Random();

        // Define los posibles desplazamientos para obtener las celdas adyacentes
        int[] possibleOffsets = { -1, 0, 1 };

        // Genera un número aleatorio entre 3 y 5 para numCoordinates
        int numCoordinates = random.nextInt(3) + 3;

        while (coordinatesSet.size() < numCoordinates) {
            // Elige un desplazamiento aleatorio para X e Y de los posibles desplazamientos
            int offsetX = possibleOffsets[random.nextInt(possibleOffsets.length)];
            int offsetY = possibleOffsets[random.nextInt(possibleOffsets.length)];
            // Calcula las nuevas coordenadas
            int newX = centerX + offsetX;
            int newY = centerY + offsetY;

            // Asegúrate de no incluir la celda original (centerX, centerY)
            // y que las nuevas coordenadas estén dentro de los límites permitidos
            if ((offsetX != 0 || offsetY != 0) && newX >= 0 && newX < 10) {
                // Crea una cadena de coordenadas única
                String coordinate = newX + "-" + newY;

                // Añade la coordenada al conjunto para asegurar la unicidad
                coordinatesSet.add(coordinate);
            }
        }

        return coordinatesSet;
    }

    private static Character[] populateCharacterArray(Character[] _characters) {
        Character[] characters;
        characters = _characters;

        // Tierra
        characters[0] = new Character("Tierra", true, true, 0, "./src/stratego/images/-10.png");
        // Héroe de Tierra
        characters[1] = new Character("Planet Tierra", false, true, 0, "./src/stratego/images/11.png");

        // Piezas Bombas
        characters[2] = new Character("Pumpkin Bomb", false, true, 11, "./src/stratego/images/-25.png");
        characters[3] = new Character("Pumpkin Bomb", false, true, 11, "./src/stratego/images/-25.png");
        characters[4] = new Character("Pumpkin Bomb", false, true, 11, "./src/stratego/images/-25.png");
        characters[5] = new Character("Pumpkin Bomb", false, true, 11, "./src/stratego/images/-25.png");
        characters[6] = new Character("Pumpkin Bomb", false, true, 11, "./src/stratego/images/-25.png");
        characters[7] = new Character("Pumpkin Bomb", false, true, 11, "./src/stratego/images/-25.png");

        // Bombas villanas
        characters[8] = new Character("Nova Blast", true, true, 11, "./src/stratego/images/35.png");
        characters[9] = new Character("Nova Blast", true, true, 11, "./src/stratego/images/35.png");
        characters[10] = new Character("Nova Blast", true, true, 11, "./src/stratego/images/35.png");
        characters[11] = new Character("Nova Blast", true, true, 11, "./src/stratego/images/35.png");
        characters[12] = new Character("Nova Blast", true, true, 11, "./src/stratego/images/35.png");
        characters[13] = new Character("Nova Blast", true, true, 11, "./src/stratego/images/35.png");

        // Rango 1
        characters[14] = new Character("Black Widow", true, true, 1, "./src/stratego/images/2.png");
        characters[15] = new Character("Black Widow", false, true, 1, "./src/stratego/images/-4.png");
        // Rango 2
        // Villanos PR 2
        characters[16] = new Character("Electro", false, true, 2, "./src/stratego/images/-11.png");
        characters[17] = new Character("Sentinel 1", false, true, 2, "./src/stratego/images/-31.png");
        characters[18] = new Character("Sentinel 2", false, true, 2, "./src/stratego/images/-40.png");
        characters[19] = new Character("Viper", false, true, 2, "./src/stratego/images/-35.png");
        characters[20] = new Character("Leader", false, true, 2, "./src/stratego/images/-16.png");
        characters[21] = new Character("Sandman", false, true, 2, "./src/stratego/images/-29.png");
        characters[22] = new Character("Ultron", false, true, 2, "./src/stratego/images/-33.png");
        characters[23] = new Character("Mr. Sinister", false, true, 2, "./src/stratego/images/-20.png");

        // Rango 3
        // Héroes PR 2
        characters[24] = new Character("Dr Strange", true, true, 2, "./src/stratego/images/8.png");
        characters[25] = new Character("Phoenix", true, true, 2, "./src/stratego/images/24.png");
        characters[26] = new Character("Storm", true, true, 2, "./src/stratego/images/31.png");
        characters[27] = new Character("Ice man", true, true, 2, "./src/stratego/images/17.png");
        characters[28] = new Character("Spider girl", true, true, 2, "./src/stratego/images/29.png");
        characters[29] = new Character("Gambit", true, true, 2, "./src/stratego/images/12.png");
        characters[30] = new Character("Nightcrawler", true, true, 2, "./src/stratego/images/23.png");
        characters[31] = new Character("Elektra", true, true, 2, "./src/stratego/images/9.png");

        // Rango 4
        // Héroes PR 3
        characters[32] = new Character("Colossus", true, true, 3, "./src/stratego/images/5.png");
        characters[33] = new Character("Beast", true, true, 3, "./src/stratego/images/1.png");
        characters[34] = new Character("She hulk", true, true, 3, "./src/stratego/images/27.png");
        characters[35] = new Character("Emma Frost", true, true, 3, "./src/stratego/images/10.png");
        characters[36] = new Character("Giant man", true, true, 3, "./src/stratego/images/14.png");

        // Rango 5
        // Villanos PR 3
        characters[37] = new Character("Lizard", false, true, 3, "./src/stratego/images/-17.png");
        characters[38] = new Character("Mole Man", false, true, 3, "./src/stratego/images/-19.png");
        characters[39] = new Character("Carnage", false, true, 3, "./src/stratego/images/-6.png");
        characters[40] = new Character("Rhino", false, true, 3, "./src/stratego/images/-27.png");
        characters[41] = new Character("Juggernaut", false, true, 3, "./src/stratego/images/-14.png");

        // Rango 6
        // Villanos PR 4
        characters[42] = new Character("SabreTooth", false, true, 4, "./src/stratego/images/-28.png");
        characters[43] = new Character("Black cat", false, true, 4, "./src/stratego/images/-3.png");
        characters[44] = new Character("Thanos", false, true, 4, "./src/stratego/images/-32.png");
        characters[45] = new Character("Abomination", false, true, 4, "./src/stratego/images/-1.png");

        // Héroes PR 4
        characters[46] = new Character("Thing", true, true, 4, "./src/stratego/images/32.png");
        characters[47] = new Character("Blade", true, true, 4, "./src/stratego/images/3.png");
        characters[48] = new Character("Punisher", true, true, 4, "./src/stratego/images/26.png");
        characters[49] = new Character("Ghost Rider", true, true, 4, "./src/stratego/images/13.png");

        // Rango 7
        // Villanos PR 5
        characters[50] = new Character("Deadpool", false, true, 5, "./src/stratego/images/-7.png");
        characters[51] = new Character("Dr Octopus", false, true, 5, "./src/stratego/images/octopus.png"); // Add the
                                                                                                           // image name
        characters[52] = new Character("Mysterio", false, true, 5, "./src/stratego/images/-21.png");
        characters[53] = new Character("Mystique", false, true, 5, "./src/stratego/images/-22.png");

        // Héroes PR 5
        characters[54] = new Character("Invisible Woman", true, true, 5, "./src/stratego/images/18.png");
        characters[55] = new Character("Cyclops", true, true, 5, "./src/stratego/images/6.png");
        characters[56] = new Character("Human Torch", true, true, 5, "./src/stratego/images/16.png");
        characters[57] = new Character("Thor", true, true, 5, "./src/stratego/images/33.png");

        // Rango 8
        // Villanos PR 6
        characters[58] = new Character("Red Skull", false, true, 6, "./src/stratego/images/-26.png");
        characters[59] = new Character("Onslaught", false, true, 6, "./src/stratego/images/-24.png");
        characters[60] = new Character("Omega Red", false, true, 6, "./src/stratego/images/-23.png");
        characters[61] = new Character("Bullseye", false, true, 6, "./src/stratego/images/-5.png");

        // Héroes PR 6
        characters[62] = new Character("Iron Man", true, true, 6, "./src/stratego/images/19.png");
        characters[63] = new Character("Hulk", true, true, 6, "./src/stratego/images/15.png");
        characters[64] = new Character("Silver Surfer", true, true, 6, "./src/stratego/images/28.png");
        characters[65] = new Character("Daredevil", true, true, 6, "./src/stratego/images/7.png");

        // Rango 9
        // Villanos PR 7
        characters[66] = new Character("Venom", false, true, 7, "./src/stratego/images/-34.png");
        characters[67] = new Character("Green Goblin", false, true, 7, "./src/stratego/images/-13.png");
        characters[68] = new Character("Apocalypse", false, true, 7, "./src/stratego/images/-2.png");

        // Héroes PR 7
        characters[69] = new Character("Namor", true, true, 7, "./src/stratego/images/21.png");
        characters[70] = new Character("Wolverine", true, true, 7, "./src/stratego/images/34.png");
        characters[71] = new Character("SpiderMan", true, true, 7, "./src/stratego/images/30.png");

        // Rango 10
        // Villanos PR 8
        characters[72] = new Character("Magneto", false, true, 8, "./src/stratego/images/-18.png");
        characters[73] = new Character("KingPin", false, true, 8, "./src/stratego/images/-15.png");

        // Héroes PR 8
        characters[74] = new Character("Nick Fury", true, true, 8, "./src/stratego/images/22.png");
        characters[75] = new Character("Professor X", true, true, 8, "./src/stratego/images/25.png");

        // Rango 11
        // Villanos PR 9
        characters[76] = new Character("Galactus", false, true, 9, "./src/stratego/images/-12.png");

        // Héroes PR 9
        characters[77] = new Character("Captain America", true, true, 9, "./src/stratego/images/4.png");

        // Rango 12
        // Villanos PR 10
        characters[78] = new Character("Dr Doom", false, true, 10, "./src/stratego/images/-8.png");

        // Héroes PR 10
        characters[79] = new Character("Mr Fantastic", true, true, 10, "./src/stratego/images/20.png");

        return characters;

    }
}
