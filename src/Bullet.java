import java.util.ArrayList;
import bagel.util.Point;

/**
 * Class to represent the bullet that the player shoots
 */
public class Bullet extends Movables {

    public static final double STEP_SIZE = 25;
    public static final int KILLING_RANGE = 25;
    private boolean isPresent = false;
    private ArrayList<Point> bulletPositions = new ArrayList<>();

    /* Constructor for the bullet class */
    /**
     * Constructor for bullet class
     * @param x x co-ord
     * @param y y co-ord
     */
    public Bullet(double x, double y) {
        super(x, y);
        setImage("res/images/shot.png");
    }


    /* Getter for isPresent */
    /**
     * Getter to check presense of bullet
     * @return A boolean which returns true if bullet is present in game
     */
    public boolean getIsPresent() {
        return isPresent;
    }

    /**
     * Getter for the list of bullet movements
     * @return An arraylist containing records of all bullet movement
     */
    public ArrayList<Point> getBulletPositions() {
        return bulletPositions;
    }

    /* Method to move the bullet forward */
    @Override
    protected void moveForward() {
        setPosX(getPosX() + STEP_SIZE * getDirectionX()) ;
        setPosY(getPosY() + STEP_SIZE * getDirectionY());
    }

    /**
     Method to implement movements of the bullet according to Algorithm 1
     * @param tomb is the board in which the game is played
     */
    /* Method that updates the location of bullet based on whether it was shot */
    public void update(ShadowTreasure tomb){
        /* Sets the position and presence of the bullet based on
        logic from Algorithm 1 */
        Zombie closestZombie = null;
        if(tomb.getZombies().size() > 0) {closestZombie = tomb.getZombies().get(0);}
        if (closestZombie != null && !this.isPresent && tomb.getZombies().size() > 0) {
            tomb.getPlayer().shootBullet();
            setPosX(tomb.getPlayer().getPosX());
            setPosY(tomb.getPlayer().getPosY());
            this.isPresent = true;
            this.setDirectionTo(closestZombie.getPos());
            bulletPositions.add(this.getPos());
        }

        if(tomb.getZombies().size() == 0) this.isPresent = false;

        if (this.isPresent) this.moveForward();
        if (closestZombie!= null &&
                this.getPos().distanceTo(closestZombie.getPos()) < KILLING_RANGE) {
            this.isPresent = false;
            {
                tomb.getZombies().remove(0);
            }
        }
        bulletPositions.add(this.getPos());
    }
}