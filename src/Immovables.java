import bagel.Image;
import bagel.util.Point;

public class Immovables implements Comparable<Immovables>{
    // image and type
    protected Image image;

    // render position
    protected Point pos;

    public double distanceToPlayer;

    protected boolean stillExists = true;

    public Immovables(double x, double y){
        this.pos = new Point(x,y);
    }

    public Point getPos() {
        return pos;
    }

    public void distanceToPlayer(Player player){
        this.distanceToPlayer = player.getPos().distanceTo(pos);
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

    public int compareTo(Immovables other){
        if (other.distanceToPlayer < this.distanceToPlayer)
            return 1;
        else if (other.distanceToPlayer > this.distanceToPlayer)
            return -1;
        else
            return 0;
    }

}

