/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stratego;

import java.util.Random;

/**
 *
 * @author marco
 */
public class Character {
    private String name;
    private boolean isHero;
    private boolean isAlive;
    private int powerRating;
    private int x;
    private int y;

    // Constructor
    public Character(String name, boolean isHero, boolean isAlive, int powerRating) {
        this.name = name;
        this.isHero = isHero;
        this.isAlive = isAlive;
        this.powerRating = powerRating;
        setCoordinates(isHero);
    }

    // if he isHero or not, heroes in rows 6-9 and columns 0-9: not heroes rows 0-3
    // and columns 0-9
    private void setCoordinates(boolean isHero) {
        if (isHero) {
            x = generateXForHero();
            y = generateYForHero();
        } else {
            x = generateXForNonHero();
            y = generateYForNonHero();
        }
    }

    private int generateXForHero() {
        // generate x for hero in rows 6-9 and columns 0-9
        return (int) (Math.random() * 10); // Adjust as needed
    }

    private int generateYForHero() {
        // generate y for hero in rows 6-9 and columns 0-9
        return (int) (Math.random() * 4) + 6; // Adjust as needed
    }

    private int generateXForNonHero() {
        // generate x for non-hero in rows 0-3 and columns 0-9
        return (int) (Math.random() * 10); // Adjust as needed
    }

    private int generateYForNonHero() {
        // generate y for non-hero in rows 0-3 and columns 0-9
        return (int) (Math.random() * 4); // Adjust as needed
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public boolean isHero() {
        return isHero;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getPowerRating() {
        return powerRating;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setHero(boolean isHero) {
        this.isHero = isHero;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setPowerRating(int powerRating) {
        this.powerRating = powerRating;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Character[] makeCharacters() {
        // Create an array of Character objects
        Character[] characters = new Character[100]; // Ajusta el tamaño del array según el número de personajes

        // Tierra
        characters[0] = new Character("Tierra", true, true, 0);
        // Héroe de Tierra
        characters[1] = new Character("Planet Tierra", false, true, 0);

        // Piezas Bombas
        characters[2] = new Character("Pumpkin Bomb", false, true, 11);
        characters[3] = new Character("Pumpkin Bomb", false, true, 11);
        characters[4] = new Character("Pumpkin Bomb", false, true, 11);
        characters[5] = new Character("Pumpkin Bomb", false, true, 11);
        characters[6] = new Character("Pumpkin Bomb", false, true, 11);
        characters[7] = new Character("Pumpkin Bomb", false, true, 11);

        // Bombas villanas
        characters[8] = new Character("Nova Blast", true, true, 11);
        characters[9] = new Character("Nova Blast", true, true, 11);
        characters[10] = new Character("Nova Blast", true, true, 11);
        characters[11] = new Character("Nova Blast", true, true, 11);
        characters[12] = new Character("Nova Blast", true, true, 11);
        characters[13] = new Character("Nova Blast", true, true, 11);

        // Rango 1
        characters[14] = new Character("Black Widow", true, true, 1);
        characters[15] = new Character("Black Widow", true, true, 1);

        // Rango 2
        // Villanos PR 2
        characters[16] = new Character("Electro", false, true, 2);
        characters[17] = new Character("Sentinel 2", false, true, 2);
        characters[18] = new Character("Sentinel 2", false, true, 2);
        characters[19] = new Character("Viper", false, true, 2);
        characters[20] = new Character("Leader", false, true, 2);
        characters[21] = new Character("Sandman", false, true, 2);
        characters[22] = new Character("Ultron", false, true, 2);
        characters[23] = new Character("Mr. Sinister", false, true, 2);

        // Rango 3
        // Héroes PR 2
        characters[24] = new Character("Dr Strange", true, true, 2);
        characters[25] = new Character("Phoenix", true, true, 2);
        characters[26] = new Character("Storm", true, true, 2);
        characters[27] = new Character("Ice man", true, true, 2);
        characters[28] = new Character("Spider girl", true, true, 2);
        characters[29] = new Character("Gambit", true, true, 2);
        characters[30] = new Character("Nightcrawler", true, true, 2);
        characters[31] = new Character("Elektra", false, true, 2);

        // Rango 4
        // Héroes PR 3
        characters[32] = new Character("Colossus", true, true, 3);
        characters[33] = new Character("Beast", true, true, 3);
        characters[34] = new Character("She hulk", true, true, 3);
        characters[35] = new Character("Emma Frost", true, true, 3);
        characters[36] = new Character("Giant man", true, true, 3);

        // Rango 5
        // Villanos PR 3
        characters[37] = new Character("Lizard", false, true, 3);
        characters[38] = new Character("Mole Man", false, true, 3);
        characters[39] = new Character("Carnage", false, true, 3);
        characters[40] = new Character("Rhino", false, true, 3);
        characters[41] = new Character("Juggernaut", false, true, 3);

        // Rango 6
        // Villanos PR 4
        characters[42] = new Character("SabreTooth", false, true, 4);
        characters[43] = new Character("Black cat", false, true, 4);
        characters[44] = new Character("Thanos", false, true, 4);
        characters[45] = new Character("Abomination", false, true, 4);

        // Héroes PR 4
        characters[46] = new Character("Thing", true, true, 4);
        characters[47] = new Character("Blade", true, true, 4);
        characters[48] = new Character("Punisher", true, true, 4);
        characters[49] = new Character("Ghost Rider", true, true, 4);
        characters[50] = new Character("Ghost Rider", true, true, 5);

        // Rango 7
        // Villanos PR 5
        characters[51] = new Character("Deadpool", false, true, 5);
        characters[52] = new Character("Dr Octopus", false, true, 5);
        characters[53] = new Character("Mysterio", false, true, 5);
        characters[54] = new Character("Mystique", false, true, 5);

        // Héroes PR 5
        characters[55] = new Character("Invisible Woman", true, true, 5);
        characters[56] = new Character("Cyclops", true, true, 5);
        characters[57] = new Character("Human Torch", true, true, 5);
        characters[58] = new Character("Thor", true, true, 5);

        // Rango 8
        // Villanos PR 6
        characters[59] = new Character("Red Skull", false, true, 6);
        characters[60] = new Character("Onslaught", false, true, 6);
        characters[61] = new Character("Omega Red", false, true, 6);
        characters[62] = new Character("Bullseye", false, true, 6);

        // Héroes PR 6
        characters[63] = new Character("Iron Man", true, true, 6);
        characters[64] = new Character("Hulk", true, true, 6);
        characters[65] = new Character("Silver Surfer", true, true, 6);
        characters[66] = new Character("Daredevil", true, true, 6);

        // Rango 9
        // Villanos PR 7
        characters[67] = new Character("Venom", false, true, 7);
        characters[68] = new Character("Green Goblin", false, true, 7);
        characters[69] = new Character("Apocalypse", false, true, 7);

        // Héroes PR 7
        characters[70] = new Character("Namor", true, true, 7);
        characters[71] = new Character("Wolverine", true, true, 7);
        characters[72] = new Character("SpiderMan", true, true, 7);

        // Rango 10
        // Villanos PR 8
        characters[73] = new Character("Magneto", false, true, 8);
        characters[74] = new Character("KingPin", false, true, 8);

        // Héroes PR 8
        characters[75] = new Character("Nick Fury", true, true, 8);
        characters[76] = new Character("Professor X", true, true, 8);

        // Rango 11
        // Villanos PR 9
        characters[77] = new Character("Galactus", false, true, 9);

        // Héroes PR 9
        characters[78] = new Character("Captain America", true, true, 9);

        // Rango 12
        // Villanos PR 10
        characters[79] = new Character("Dr Doom", false, true, 10);

        // Héroes PR 10
        characters[80] = new Character("Mr Fantastic", true, true, 10);

        return characters;
    }
}
