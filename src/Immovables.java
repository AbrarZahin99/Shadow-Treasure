import bagel.Image;
import bagel.util.Point;

public class Immovables {
    // image and type
    protected static Image image;

    // render position
    protected Point pos;

    protected boolean stillExists;

    public Immovables(double x, double y){
        this.pos = new Point(x,y);
    }


    public void setPos(Point pos) {
        this.pos = pos;
    }


    // render image
    public void draw() {
        image.drawFromTopLeft(pos.x, pos.y);
    }

    public boolean meets(Player player) {
        boolean hasMet = false;
        double distanceToPlayer = player.getPos().distanceTo(pos);
        if (distanceToPlayer < ShadowTreasure.ClOSENESS) {
            hasMet = true;
        }
        return hasMet;
    }

}

