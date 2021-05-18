import bagel.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An example Bagel game.
 */
public class ShadowTreasure extends AbstractGame {

    private final Image BACKGROUND = new Image("res/images/background.png");
    public static final int ClOSENESS = 50;
    public static final int SHOOTING_RANGE = 150;

    // for rounding double number
  //  private static final DecimalFormat df = new DecimalFormat("0.00");

    // tick cycle and var
    private final int TICK_CYCLE = 2;
    private int tick;

    // list of characters
    private Player player;
    private ArrayList<Sandwich> sandwiches = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private Treasure treasure;
    private Bullet bullet;


    public Player getPlayer() {
        return player;
    }
    public ArrayList<Sandwich> getSandwiches() {
        return sandwiches;
    }
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }
    public Bullet getBullet() {
        return bullet;
    }
    public Treasure getTreasure() {
        return treasure;
    }

    public ShadowTreasure() throws IOException {
        //super(900, 600, "Treasure Hunt");
        this.loadEnvironment("res/IO/environment.csv");
        this.tick = 1;
       // System.out.println(player.getPos().x + "," + player.getPos().y + "," + player.getEnergy());
        bullet = new Bullet(player.getPosX(), player.getPosY());
    }

    /**
     * Load from input file
     */
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
     */
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
                  //  System.out.println(df.format(player.getPos().x) + ","
               //             + df.format(player.getPos().y) + "," + player.getEnergy());

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
