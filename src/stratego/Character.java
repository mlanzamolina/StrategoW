package stratego;

/**
 *
 * @author marco
 */
import javax.swing.ImageIcon;

public class Character {
    private String name;
    private boolean isHero;
    private boolean isAlive;
    private int powerRating;
    private int x; // Initialize with default values
    private int y;
    private ImageIcon imagen;
    private boolean moveable;
    private boolean ortogonal;
    private boolean show;

    // Constructor
    public Character(String name, boolean isHero, boolean isAlive, int powerRating, String FichasImagenes) {
        this.name = name;
        this.isHero = isHero;
        this.isAlive = isAlive;
        this.powerRating = powerRating;
        this.x = -1;
        this.y = -1;
        this.imagen = new ImageIcon(FichasImagenes);
        this.moveable = true;
        this.ortogonal = true;
        this.show = true;
        // setCoordinates(isHero);
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

    public ImageIcon getImage() {
        return imagen;
    }

    public boolean getMoveable() {
        return moveable;
    }

    public boolean getOrtogonal() {
        return ortogonal;
    }
    public boolean getShow() {
        return show;
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

    public void setImage(String image) {
        this.imagen = new ImageIcon(image);
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public void setOrtogonal(boolean ortogonal) {
        this.ortogonal = ortogonal;
    }
    public void setShow(boolean show) {
        this.show = show;
    }

    public void printCharacterStates() {
        System.out.println("Nombre: " + this.name);
        System.out.println("Es héroe: " + this.isHero);
        System.out.println("Está vivo: " + this.isAlive);
        System.out.println("Rango: " + this.powerRating);
        System.out.println("Coordenada X: " + this.x);
        System.out.println("Coordenada Y: " + this.y);
    }

}
