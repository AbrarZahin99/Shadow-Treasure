import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.Collections;

public class Player extends Movables{

    // image source file
    public static final String FILENAME = "res/images/player.png";
    // speed
    public static final double STEP_SIZE = 10;
    // energy level threshold
    private static final int LOWENERGY = 3;
    private boolean treasure_reached = false;
    // healthbar font
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();



    // healthbar parameters
    private int energy;
    private Zombie closestZombie;
    private Sandwich closestSandwich;

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

    public void update(ShadowTreasure tomb){
        // Check if the player meets the Zombie and if so reduce energy by 3 and
        // terminate. Otherwise if the player meets the Sandwich increase the energy
        // an set the Sandwich to invisible
     //   Collections.sort(tomb.zombies);
        closestZombie = tomb.zombies.get(0);
        closestSandwich = tomb.sandwiches.get(0);

        if (treasure_reached || (tomb.sandwiches.size()==0 &&
                tomb.zombies.size() >0 && energy < 3)) {
            render

            if (closestZombie != null && closestZombie.meets(this)) {
                shootBullet();
                tomb.zombies.remove(0);

            } else if (closestSandwich != null && closestSandwich.meets(this)) {
                eatSandwich();
                tomb.sandwiches.remove(0);
            }
            // set direction
            if (this.energy >= LOWENERGY) {
                // direction to zombie
                setDirectionTo(closestZombie.getPos());
            } else {
                // direction to sandwich
                setDirectionTo(closestSandwich.getPos());
            }
            // move one step
            moveForward();
        }
    }


    /* Renders the images of the player and the font */
    @Override
    public void render() {
        image.drawFromTopLeft(posX, posY);
        // also show energy level
        if (!treasure_reached) {
            FONT.drawString("energy: " + energy, 20, 760, OPT.setBlendColour(Colour.BLACK));
        }
        if (treasure_reached) {
            FONT.drawString("energy: " + energy +
                    "success!", 20, 760, OPT.setBlendColour(Colour.BLACK));
        }
    }

    public void eatSandwich(){
        energy += 5;
    }

    public void shootBullet(){
        energy -= 3; }

    public void treasure_reached(){
        this.treasure_reached = true:
    }
}
