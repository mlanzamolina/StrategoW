/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stratego;

/**
 *
 * @author marco
 */
public class Character {
        // Fields
    private String name;
    private boolean isHero;
    private boolean isAlive;
    private int powerRating;
    private int[] initialState;

    // Constructor
    public Character(String name, boolean isHero, boolean isAlive, int powerRating, int x, int y) {
        this.name = name;
        this.isHero = isHero;
        this.isAlive = isAlive;
        this.powerRating = powerRating;
        this.initialState = new int[]{x, y};
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

    public int[] getInitialState() {
        return initialState;
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

    public void setInitialState(int x, int y) {
        this.initialState[0] = x;
        this.initialState[1] = y;
    }
    
    public static Character[] makeCharacters() {
     // Create an array of Character objects
        Character[] characters = new Character[40];  // Adjust the array size based on the number of characters

        // Rango 1
        characters[0] = new Character("black widow", true, true, 1, 5, 10);

        // Rango 2
        // Villains PR 2
        characters[1] = new Character("Electro", false, true, 2, 8, 15);
        characters[2] = new Character("Sentinel 2", false, true, 2, 8, 15);
        characters[3] = new Character("Viper", false, true, 2, 8, 15);
        characters[4] = new Character("Leader", false, true, 2, 8, 15);
        characters[5] = new Character("Sandman", false, true, 2, 8, 15);
        characters[6] = new Character("Ultron", false, true, 2, 8, 15);
        characters[7] = new Character("Mr.Sinister", false, true, 2, 8, 15);
        characters[8] = new Character("Nightcrawler", true, true, 2, 8, 15);
        characters[9] = new Character("Elektra", false, true, 2, 8, 15);

        // Rango 3
        // Heroes PR 2
        characters[10] = new Character("Dr Strange", true, true, 2, 8, 15);
        characters[11] = new Character("Phoenix", true, true, 2, 8, 15);
        characters[12] = new Character("Storm", true, true, 2, 8, 15);
        characters[13] = new Character("Ice man", true, true, 2, 8, 15);
        characters[14] = new Character("Spider girl", true, true, 2, 8, 15);
        characters[15] = new Character("Gambit", true, true, 2, 8, 15);

        // Rango 4
        // Heroes PR 3
        characters[16] = new Character("Colossus", true, true, 3, 8, 15);
        characters[17] = new Character("Beast", true, true, 3, 8, 15);
        characters[18] = new Character("She hulk", true, true, 3, 8, 15);
        characters[19] = new Character("Emma Frost", true, true, 3, 8, 15);
        characters[20] = new Character("Giant man", true, true, 3, 8, 15);

        // Rango 5
        // Villains PR 3
        characters[21] = new Character("Lizard", false, true, 3, 8, 15);
        characters[22] = new Character("Mole Man", false, true, 3, 8, 15);
        characters[23] = new Character("Carnage", false, true, 3, 8, 15);
        characters[24] = new Character("Rhino", false, true, 3, 8, 15);
        characters[25] = new Character("Juggernaut", false, true, 3, 8, 15);

        // Rango 6
        // Villains PR 4
        characters[26] = new Character("SabreTooth", false, true, 4, 8, 15);
        characters[27] = new Character("Black cat", false, true, 4, 8, 15);
        characters[28] = new Character("Thanos", false, true, 4, 8, 15);
        characters[29] = new Character("Abomination", false, true, 4, 8, 15);

        // Heroes PR 4
        characters[30] = new Character("Thing", true, true, 4, 8, 15);
        characters[31] = new Character("Blade", true, true, 4, 8, 15);
        characters[32] = new Character("Punisher", true, true, 4, 8, 15);
        characters[33] = new Character("Ghost Rider", true, true, 4, 8, 15);
        characters[34] = new Character("Ghost Rider", true, true, 5, 8, 15);

        // Rango 7
        // Villains PR 5
        characters[35] = new Character("Deadpool", false, true, 5, 8, 15);
        characters[36] = new Character("Dr Octopus", false, true, 5, 8, 15);
        characters[37] = new Character("Mysterio", false, true, 5, 8, 15);
        characters[38] = new Character("Mystique", false, true, 5, 8, 15);

        // Heroes PR 5
        characters[39] = new Character("Invisible Woman", true, true, 5, 8, 15);
        characters[40] = new Character("Cyclops", true, true, 5, 8, 15);
        characters[41] = new Character("Human Torch", true, true, 5, 8, 15);
        characters[42] = new Character("Thor", true, true, 5, 8, 15);

        // Rango 8
        // Villains PR 6
        characters[43] = new Character("Red Skull", false, true, 6, 8, 15);
        characters[44] = new Character("Onslaught", false, true, 6, 8, 15);
        characters[45] = new Character("Omega Red", false, true, 6, 8, 15);
        characters[46] = new Character("Bullseye", false, true, 6, 8, 15);

        // Heroes PR 6
        characters[47] = new Character("Iron Man", true, true, 6, 8, 15);
        characters[48] = new Character("Hulk", true, true, 6, 8, 15);
        characters[49] = new Character("Silver Surfer", true, true, 6, 8, 15);
        characters[50] = new Character("Daredevil", true, true, 6, 8, 15);

        // Rango 9
        // Villains PR 7
        characters[51] = new Character("Venom", false, true, 7, 8, 15);
        characters[52] = new Character("Green Goblin", false, true, 7, 8, 15);
        characters[53] = new Character("Apocalypse", false, true, 7, 8, 15);

        // Heroes PR 7
        characters[54] = new Character("Namor", true, true, 7, 8, 15);
        characters[55] = new Character("Wolverine", true, true, 7, 8, 15);
        characters[56] = new Character("SpiderMan", true, true, 7, 8, 15);

        // Rango 10
        // Villains PR 8
        characters[57] = new Character("Magneto", false, true, 8, 8, 15);
        characters[58] = new Character("KingPin", false, true, 8, 8, 15);

        // Heroes PR 8
        characters[59] = new Character("Nick Fury", true, true, 8, 8, 15);
        characters[60] = new Character("Professor X", true, true, 8, 8, 15);

        // Rango 11
        // Villains PR 9
        characters[61] = new Character("Galactus", false, true, 9, 8, 15);

        // Heroes PR 9
        characters[62] = new Character("Captain America", true, true, 9, 8, 15);

        // Rango 12
        // Villains PR 10
        characters[63] = new Character("Dr Doom", false, true, 10, 8, 15);

        // Heroes PR 10
        characters[64] = new Character("Mr Fantastic", true, true, 10, 8, 15);
        
        return characters;
    }
}
