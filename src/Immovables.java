import bagel.Image;
import bagel.util.Point;

/**
 * Provides a template for inheritance for classes that represent objects that don't move
 */
public class Immovables implements Comparable<Immovables>{
    // image and type
    private Image image;

    // render position
    private final Point pos;

    // Keeps record of player's distance from each zombie
    private double distanceToPlayer;

    // Constructor for the Immovable class

    /**
     * Constructor for objects inheriting from the Immovable class
     * @param x the x co-ordinate
     * @param y the y co-ordinate
     */
    public Immovables(double x, double y){
        this.pos = new Point(x,y);
    }

    // Getters and setters

    /**
     * Getter to access the point attribute
     * @return the current object's point location
     */
    public Point getPos() {
        return pos;
    }

    protected void setImage(String string) {
        this.image = new Image(string);
    }

    /**
     * Getter than returns the distance to player
     * @return a double representing the distance to the player
     */
    public double getDistanceToPlayer() {
        return distanceToPlayer;
    }

    /**
     * Gives access to other class to change the distanceToPlayer attribute
     * @param distanceToPlayer input the new distance to player
     */
    public void setDistanceToPlayer(double distanceToPlayer) {
        this.distanceToPlayer = distanceToPlayer;
    }

    /**
     * Method to calculate distance from player to immovable objects
     * @param player Player object from the shadowTreasure game
     * @return a double to represent distance from the player
     */
    // Calculates distance to Player
    public double distanceToPlayer(Player player){
        return player.getPos().distanceTo(pos);
    }

    /**
     * Method to draw the images at their specified locations
     */
    // renders image
    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }

    // The code below has some parts copied from sample solution
    // Checks whether player is close enough
    /**
     * Methods
     * @param player The player with whom the closeness is checked
     * @return A boolean to check whether the object is close enough
     */
    public boolean meets(Player player) {
        boolean hasMet = false;
        double distanceToPlayer = player.getPos().distanceTo(pos);
        if (distanceToPlayer < ShadowTreasure.CLOSENESS) {
            hasMet = true;
        }
        return hasMet;
    }

    /* Compares Immovable objects based on distance to player, which has been
     implemented in order to sort the arraylists using comparable interface */
    /**
     * Method that compares two objects of immovable class
     * @param other the other immovable object against which the current object is being compared
     * @return A boolean value determining which object is closer
     */
    public int compareTo(Immovables other){
        return Double.compare(this.distanceToPlayer, other.distanceToPlayer);
    }

}

