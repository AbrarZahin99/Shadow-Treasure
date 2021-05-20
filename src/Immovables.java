import bagel.Image;
import bagel.util.Point;

public class Immovables implements Comparable<Immovables>{
    // image and type
    private Image image;

    // render position
    private final Point pos;

    // Keeps record of player's distance from each zombie
    private double distanceToPlayer;

    // Constructor for the Immovable class
    public Immovables(double x, double y){
        this.pos = new Point(x,y);
    }

    // Getters and setters
    public Point getPos() {
        return pos;
    }

    protected void setImage(String string) {
        this.image = new Image(string);
    }

    public double getDistanceToPlayer() {
        return distanceToPlayer;
    }

    public void setDistanceToPlayer(double distanceToPlayer) {
        this.distanceToPlayer = distanceToPlayer;
    }

    // Calculates distance to Player
    public double distanceToPlayer(Player player){
        return player.getPos().distanceTo(pos);
    }
    // renders image
    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }

    // Checks whether player is close enough
    public boolean meets(Movables movable) {
        boolean hasMet = false;
        double distanceToPlayer = movable.getPos().distanceTo(pos);
        if (distanceToPlayer < ShadowTreasure.CLOSENESS) {
            hasMet = true;
        }
        return hasMet;
    }

    // Compares Immovable objects based on distance to player, which has been
    // implemented in order to sort the arraylists using comparable interface
    public int compareTo(Immovables other){
        return Double.compare(this.distanceToPlayer, other.distanceToPlayer);
    }

}

