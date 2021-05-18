import bagel.*;
import bagel.Font;
import bagel.util.Colour;
import bagel.util.Point;


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
    public Player(double x, double y, int energy) {
        super(x,y);
        this.energy = energy;
        setImage("res/images/player.png");
    }

    public Point getPos(){
        return new Point(getPosX(), getPosY());
    }

    /* Method to move the player forward */
    @Override
    protected void moveForward(){
        setPosX(getPosX() + STEP_SIZE * getDirectionX()) ;
        setPosY(getPosY() + STEP_SIZE * getDirectionY());
    }

    public void update(ShadowTreasure tomb){
        Zombie closestZombie;
        Sandwich closestSandwich;

        if(tomb.getZombies().size() > 0) {closestZombie = tomb.getZombies().get(0);}
        else{ closestZombie = null;}
        if(tomb.getSandwiches().size() >0) {closestSandwich = tomb.getSandwiches().get(0);}
        else{closestSandwich = null;}
        if (tomb.getTreasure().meets(this) && tomb.getZombies().size() == 0){
            treasure_reached = true;
        }
        if (treasure_reached || (tomb.getSandwiches().size()==0 && tomb.getZombies().size() >0
                && energy < LOW_ENERGY && (!tomb.getBullet().getIsPresent()))) {
            System.out.print(energy);
            if (treasure_reached){
                System.out.println(",success!");
            }
            Window.close();}
        else {
            if (closestSandwich != null && closestSandwich.meets(this)) {
                eatSandwich();
                tomb.getSandwiches().remove(0);
            }
            else if ((closestZombie != null && closestZombie.getDistanceToPlayer()
                    < ShadowTreasure.SHOOTING_RANGE && this.energy >= LOW_ENERGY)
                    || tomb.getBullet().getIsPresent()) {
                tomb.getBullet().update(tomb);
            }
  //          if (tomb.bullet.isPresent){System.out.println(tomb.bullet);}
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
    @Override
    public void render() {
        getImage().drawFromTopLeft(getPosX(), getPosY());
        // also show energy level
        if (!treasure_reached) {
            FONT.drawString("energy: " + energy, 20, 760, OPT.setBlendColour(Colour.BLACK));
        }
    }

    private void eatSandwich(){
        energy += 5;
    }

    public void shootBullet(){
        energy -= 3;
    }

}
