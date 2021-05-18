import bagel.Image;
import bagel.util.Point;

public abstract class Movables {

    // image
    private Image image;
    // render position
    private double posX;
    private double posY;
    // direction
    private double directionX;
    private double directionY;

    protected Movables(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    protected void setImage(String string) {
        this.image = new Image(string);
    }

    protected Image getImage() {
        return image;
    }

    protected double getPosX() {
        return posX;
    }

    protected void setPosX(double posX) {
        this.posX = posX;
    }

    protected double getPosY() {
        return posY;
    }

    protected void setPosY(double posY) {
        this.posY = posY;
    }

    protected double getDirectionX() {
        return directionX;
    }

    protected double getDirectionY() {
        return directionY;
    }

    // Method to return the x,y co-ordinates as a point
    public Point getPos(){
        return new Point(posX, posY);
    }

    /* Points towards a certain point */
    protected void setDirectionTo(Point Dest){
        double Len = new Point(posX, posY).distanceTo(Dest);
        this.directionX = (Dest.x - posX)/Len;
        this.directionY = (Dest.y - posY)/Len;
    }

    /* Method to move the player forward */
    protected abstract void moveForward();

    /* Method to render the image */
    public void render(){
        image.drawFromTopLeft(posX, posY);
    }

}
