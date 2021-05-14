import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

public class Player extends Movables{

    // image source file
    public static final String FILENAME = "res/images/player.png";
    // speed
    public static final double STEP_SIZE = 10;
    // energy level threshold
    private static final int LOWENERGY = 3;
    // zero vector
    // healthbar font
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();


    // healthbar parameters
    private int energy;

    public Player(double x, double y, int energy) {
        super(x,y);
        this.energy = energy;
        image = new Image("res/images/player.png");
    }

    public int getEnergy(){
        return this.energy;
    }

    public Point getPos(){
        return new Point(posX, posY);
    }

    /* Method to move the player forward */
    public void moveForward(){
        posX += STEP_SIZE * directionX;
        posY += STEP_SIZE * directionY;
    }

    /* Renders the images of the player and the font */
    @Override
    public void render() {
        image.drawFromTopLeft(posX, posY);
        // also show energy level
        FONT.drawString("energy: "+ energy,20,760, OPT.setBlendColour(Colour.BLACK));
    }

    public void eatSandwich(){
        energy += 5;
    }

    public void shootBullet(){
        energy -= 3; }
}
