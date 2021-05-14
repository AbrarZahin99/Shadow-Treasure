import bagel.Image;
import bagel.util.Point;

public class Sandwich extends Immovables{
    // image

    // render position
    private Point pos;

    public Sandwich(double x, double y){
        super(x,y);
        this.stillExists = true;
        image = new Image("res/images/sandwich.png");
    }

    public Point getPos() {
        return pos;
    }
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visiblility) {
        this.visible = visiblility;
    }

    // render image
    public void draw() {
        if (visible) {
            image.drawFromTopLeft(pos.x, pos.y);
        }
    }

    public boolean meets(Player player) {
        boolean hasMet = false;

        if (visible){
            double distanceToPlayer = player.getPos().distanceTo(pos);
            if (distanceToPlayer < ShadowTreasure.ClOSENESS) {
                hasMet = true;
            }
        }
        return hasMet;
    }


}
