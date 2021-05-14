import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;


public class Bullet extends Movables {

    public static final double STEP_SIZE = 25;
    private boolean isPresent;


    public Bullet(double x, double y, int energy) {
        super(x, y);
        image = new Image("res/images/bullet.png");
    }
    /* Getters and setters */
    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
    /* Method to move the bullet forward */
    public void moveForward() {
        posX += STEP_SIZE * directionX;
        posY += STEP_SIZE * directionY;
    }
}