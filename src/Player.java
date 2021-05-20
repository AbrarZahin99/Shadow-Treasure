import bagel.*;
import bagel.Font;
import bagel.util.Colour;
import bagel.util.Point;

/**
 * Represents the moving player of the game
 */
public class Player extends Movables{

    // Steps moved every tick_cycle
    public static final double STEP_SIZE = 10;
    // energy level threshold
    public static final int LOW_ENERGY = 3;

    //  font parameters
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();

    // displays the energy
    private int energy;

    // boolean to keep track of whether treasure was reached
    private boolean treasure_reached = false;

    // constructor for the player class
    /**
     *
     * @param x the x co-ord of player
     * @param y the y co-ord of player
     * @param energy Current energy level of the player
     */
    public Player(double x, double y, int energy) {
        super(x,y);
        this.energy = energy;
        setImage("res/images/player.png");
    }

    /**
     *
     * @return returns a Point class for use in other methods
     */
    public Point getPos(){
        return new Point(getPosX(), getPosY());
    }

    /* Method to move the player forward */
    @Override
    protected void moveForward(){
        setPosX(getPosX() + STEP_SIZE * getDirectionX()) ;
        setPosY(getPosY() + STEP_SIZE * getDirectionY());
    }

    // Some code has been copied from sample solution
    /**
     * Method to implement movements of the player according to Algorithm 1
     * @param tomb is the board in which the game is played
     */
    public void update(ShadowTreasure tomb){
        Zombie closestZombie;
        Sandwich closestSandwich;

        // Gets closestZombie and closestSandwich
        if(tomb.getZombies().size() > 0) { closestZombie = tomb.getZombies().get(0);}
        else{ closestZombie = null;}
        if(tomb.getSandwiches().size() >0) { closestSandwich = tomb.getSandwiches().get(0);}
        else{closestSandwich = null;}
        //
        if (tomb.getTreasure().meets(this) && tomb.getZombies().size() == 0){
            treasure_obtained();
        }
        // Code to check end of game
        if (treasure_reached || (tomb.getSandwiches().size()==0 && tomb.getZombies().size() >0
                && energy < LOW_ENERGY && (!tomb.getBullet().getIsPresent()))) {
            System.out.print(energy);
            tomb.setEndOfGame(true);
            if (treasure_reached){
                System.out.println(",success!");
            }
            Window.close();}
        else
            // Code determining whether player close enough to shoot or eat
            {
            if (closestSandwich != null && closestSandwich.meets(this)) {
                eatSandwich();
                tomb.getSandwiches().remove(0);
            }
            else if ((closestZombie != null && closestZombie.getDistanceToPlayer()
                    < ShadowTreasure.SHOOTING_RANGE && this.energy >= LOW_ENERGY)
                    || tomb.getBullet().getIsPresent()) {
                tomb.getBullet().update(tomb);
            }
            // Code determining which direction the player should go to
            if (tomb.getZombies().size() == 0){
                setDirectionTo(tomb.getTreasure().getPos());
            }
            else if (this.energy >= LOW_ENERGY && closestZombie != null) {
                // direction to zombie
                setDirectionTo(closestZombie.getPos());
            } else if (closestSandwich != null){
                // direction to sandwich
                setDirectionTo(closestSandwich.getPos());
            }
            // move one step
            moveForward();
        }
    }


    /* Renders the images of the player and the font */
    /**
     * Overriden method to render the player's location and display the energy state
     */
    @Override
    public void render() {
        getImage().drawFromTopLeft(getPosX(), getPosY());
        // also show energy level
        if (!treasure_reached) {
            FONT.drawString("energy: " + energy, 20, 760, OPT.setBlendColour(Colour.BLACK));
        }
    }
    // Eats and increases energy by 5
    private void eatSandwich(){
        energy += 5;
    }
    //  Shoots and decreases energy by 3

    /**
     * Reduces Player's energy by 1 upon shooting the bullet
     */
    public void shootBullet(){
        energy -= 3;
    }
    // Treasure obtained and game ends at the start of next turn
    private void treasure_obtained(){
        treasure_reached = true;
    }

}
