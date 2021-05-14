import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

public abstract class Movables {

    // image and type
    protected static Image image;
    // render position
    private Point pos;
    // direction
    private double directionX;
    private double directionY;


    public Movables(double x, double y) {
        this.pos = new Point(x,y);
    }



    public void setDirectionTo(Point Dest){
        double Len = pos.distanceTo(Dest);
        directionX = (Dest.x - pos.x)/Len;
        directionY = (Dest.y - pos.y)/Len;
    }
    /* Method to move the player forward */
    public abstract void moveForward();


    public void render(){
        image.drawFromTopLeft(pos.x, pos.y);
    }

}
