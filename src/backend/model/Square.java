package backend.model;

public abstract class Square extends Rectangle {
    private static final String TYPE = "Square";
    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
        this.figureName=TYPE;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

}
