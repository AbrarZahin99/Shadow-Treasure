import bagel.Image;
import bagel.util.Point;

public class Sandwich extends Immovables{

    public Sandwich(double x, double y) {
        super(x, y);
        image = new Image("res/images/sandwich.png");
        this.stillExists = true;
    }

    public boolean eaten(Player player){
        boolean isEaten = false;
        double distanceToplayer = player.getPos().distanceTo(pos);
        if (distanceToplayer < ShadowTreasure.ClOSENESS) {
            isEaten = true;
        }
        return isEaten;
    }

}
