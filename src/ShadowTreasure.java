import bagel.*;
import bagel.util.Point;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An example Bagel game.
 */
// Some methods have been copied from Project-1 sample solution
public class ShadowTreasure extends AbstractGame {

    // Set of declared constants
    private final Image BACKGROUND = new Image("res/images/background.png");
    public static final int CLOSENESS = 50;
    public static final int SHOOTING_RANGE = 150;

    // for rounding double number
    private static final DecimalFormat df = new DecimalFormat("0.00");

    // tick cycle and var
    private final int TICK_CYCLE = 10;
    private int tick;

    // State to keep track of end of game
    private boolean endOfGame = false;

    // list of entities
    private Player player;
    private ArrayList<Sandwich> sandwiches = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private Treasure treasure;
    private Bullet bullet;

    // List of getters and setters to be used in bullet and player classes

    /**
     * Getter for player in the shadowtreasure game
     * @return the player exisiting in this game
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter to access the arraylist
     * @return an arraylist consisting of all sandwiches
     */
    public ArrayList<Sandwich> getSandwiches() {
        return sandwiches;
    }

    /**
     * Getter to access the arraylist
     * @return an arraylist consisting of all zombies
     */
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    /**
     * Getter to access the bullet in the shadowtreasure game
     * @return The bullet that exists in this game
     */
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * Getter to access the game's treasure
     * @return The treasure that exists in this game
     */
    public Treasure getTreasure() {
        return treasure;
    }

    /**
     * Setter for the end of game status attribute
     * @param endOfGame A boolean variable which is set to true
     * if required conditions for the game to end are met
     */
    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
    }

    /**
     * Consturctor for ShadowTreasure
     * @throws IOException
     */

    public ShadowTreasure() throws IOException {
        //super(900, 600, "Treasure Hunt");
        this.loadEnvironment("res/IO/environment.csv");
        this.tick = 1;
        bullet = new Bullet(player.getPosX(), player.getPosY());
    }

    /**
     * Load from input file
     */
    // Copied from sample solution
    private void loadEnvironment(String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                type = type.replaceAll("[^a-zA-Z0-9]", ""); // remove special characters
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                switch (type) {
                    case "Player" -> this.player = new Player(x, y, Integer.parseInt(parts[3]));
                    case "Zombie"  -> zombies.add(new Zombie(x,y));
                    case "Sandwich"  -> sandwiches.add(new Sandwich(x, y));
                    case "Treasure" -> this.treasure = new Treasure(x,y);
                    default    -> throw new BagelError("Unknown type: " + type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Performs a state update.
     * @param input A bagel class
     */
    // Some blocks for update has been copied from sample solution
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        } else{
            // Draw background
            BACKGROUND.drawFromTopLeft(0, 0);
            // Update status when the TICK_CYCLE is up
            if (tick > TICK_CYCLE) {
                for(Zombie z: zombies) {
                    z.setDistanceToPlayer(z.distanceToPlayer(player));
                }
                for(Sandwich s: sandwiches) {
                    s.setDistanceToPlayer(s.distanceToPlayer(player));
                }
                Collections.sort(sandwiches);
                Collections.sort(zombies);
                // update player status
                player.update(this);
                tick = 1;
            }
            tick++;
            for (Zombie zombie : zombies) {
                zombie.draw();
            }
            for (Sandwich sandwich : sandwiches) {
                sandwich.draw();
            }
            treasure.draw();
            player.render();
            if(bullet.getIsPresent()){bullet.render();}

            if(endOfGame){
                FileWriter writer;
                try {
                    writer = new FileWriter("res/IO/output.csv");
                    for(Point point : bullet.getBulletPositions()){
                    writer.write(df.format(point.x) + ","
                            + df.format(point.y) + "\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }
}
