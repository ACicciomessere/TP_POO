package backend.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Rectangle extends Figure {

    private Point topLeft, bottomRight;
    private static final String TYPE = "Rectangle";
    public Rectangle(Layer layer, Point topLeft, Point bottomRight) {
        super(layer);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.figureName=TYPE;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void moveRectangle(double diffX, double diffY){
        this.topLeft = new Point(topLeft.getX() + diffX, topLeft.getY() + diffY);
        this.bottomRight = new Point(bottomRight.getX() + diffX, bottomRight.getY() + diffY);
    }

    @Override
    public boolean pointBelongs(Point eventPoint) {
        return eventPoint.getX() > getTopLeft().getX() && eventPoint.getX() < getBottomRight().getX() &&
                eventPoint.getY() > getTopLeft().getY() && eventPoint.getY() < getBottomRight().getY();
    }

    @Override
    public void fill(GraphicsContext gc) {
        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

    @Override
    public void stroke(GraphicsContext gc) {
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

    @Override
    public void updateCoordinates(double diffX, double diffY) {
        getTopLeft().changeX(diffX);
        getBottomRight().changeX(diffX);
        getTopLeft().changeY(diffY);
        getBottomRight().changeY(diffY);
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

}
