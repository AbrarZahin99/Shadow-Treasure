import bagel.Image;
import bagel.util.Point;

public class Zombie extends Immovables{


    public Zombie(double x, double y) {
        super(x, y);
        image = new Image("res/images/player.png");
    }

    public boolean shot(Bullet bullet){
        boolean isShot = false;
        double distanceToBullet = Bullet.getPos().distanceTo(pos);
        if (distanceToBullet < ShadowTreasureComplete.ClOSENESS) {
            isShot = true;
        }
        return isShot;

    }

}

