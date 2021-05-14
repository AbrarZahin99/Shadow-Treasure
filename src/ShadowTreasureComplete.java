import bagel.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * An example Bagel game.
 */
public class ShadowTreasureComplete extends AbstractGame {

    private final Image BACKGROUND = new Image("res/images/background.png");
    public static final int ClOSENESS = 50;

    // for rounding double number
    private static DecimalFormat df = new DecimalFormat("0.00");

    // tick cycle and var
    private final int TICK_CYCLE = 10;
    private int tick;

    // list of characters
    private Player player;
    private ArrayList<Sandwich> sandwiches = new ArrayList<Sandwich>();
    private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    private Treasure treasure;

    // end of game indicator
    private boolean endOfGame;

    public ShadowTreasureComplete() throws IOException {
        //super(900, 600, "Treasure Hunt");
        this.loadEnvironment("res/IO/environment.csv");
        this.tick = 1;
        this.endOfGame = false;
        System.out.println(player.getPos().x + "," + player.getPos().y + "," + player.getEnergy());
    }


    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
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
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                switch (type) {
                    case "Player" -> this.player = new Player(x, y, Integer.parseInt(parts[3]));
                    case "Zombie"  -> zombies.add(new Zombie(x,y));
                    case "Sandwich"  -> sandwiches.add(new Sandwich(x, y));
                   // case "Treasure" -> this.treasure = new Treasure(x,y);
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
        if (this.endOfGame || input.wasPressed(Keys.ESCAPE)){
            Window.close();
        } else{
            // Draw background
            BACKGROUND.drawFromTopLeft(0, 0);
            // Update status when the TICK_CYCLE is up
            if (tick > TICK_CYCLE) {
                // update player status
                player.update(this);
                tick = 1;
                System.out.println(df.format(player.getPos().x) + "," + df.format(player.getPos().y) + "," + player.getEnergy());
            }
            tick++;
            sandwich.draw();
            zombie.draw();
            player.render();
        }
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasureComplete game = new ShadowTreasureComplete();
        game.run();
    }
}
