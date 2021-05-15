import bagel.Image;
import bagel.util.Point;

public class Zombie extends Immovables{

    public Zombie(double x, double y) {
        super(x, y);
        image = new Image("res/images/zombie.png");
        this.stillExists = true;
    }

    public boolean shot(Player bullet){
        boolean isShot = false;
        double distanceToBullet = bullet.getPos().distanceTo(pos);
        if (distanceToBullet < ShadowTreasure.ClOSENESS) {
            isShot = true;
        }
        return isShot;
    }


}

