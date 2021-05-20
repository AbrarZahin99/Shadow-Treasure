import java.util.ArrayList;
import bagel.util.Point;

public class Bullet extends Movables {

    public static final double STEP_SIZE = 25;
    public static final int KILLING_RANGE = 25;
    private boolean isPresent = false;
    private ArrayList<Point> bullets = new ArrayList<>();

    /* Constructor for the bullet class */
    public Bullet(double x, double y) {
        super(x, y);
        setImage("res/images/shot.png");
    }

    /* Getter for isPresent */
    public boolean getIsPresent() {
        return isPresent;
    }

    public ArrayList<Point> getBullets() {
        return bullets;
    }

    /* Method to move the bullet forward */
    @Override
    protected void moveForward() {
        setPosX(getPosX() + STEP_SIZE * getDirectionX()) ;
        setPosY(getPosY() + STEP_SIZE * getDirectionY());
    }

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
            bullets.add(this.getPos());
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
        bullets.add(this.getPos());
    }
    @Override
    public String toString() {
        return "Bullet{" +
                "posX=" + getPosX() +
                ", posY=" + getPosY() +
                '}';
    }
}