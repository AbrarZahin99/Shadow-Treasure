import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

public abstract class Movables {

    // image
    protected static Image image;
    // render position
    protected double posX;
    protected double posY;
    // direction
    protected double directionX;
    protected double directionY;

    public Movables(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /* Points towards a certain point */
    public void setDirectionTo(Point Dest){
        double Len = new Point(posX, posY).distanceTo(Dest);
        directionX = (Dest.x - posX)/Len;
        directionY = (Dest.y - posY)/Len;
    }

    /* Method to move the player forward */
    public abstract void moveForward();

    /* Method to render the image */
    public void render(){
        image.drawFromTopLeft(posX, posY);
    }

}
