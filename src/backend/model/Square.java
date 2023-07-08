package backend.model;

public abstract class Square extends Rectangle {
    private static final String TYPE = "Square";
    private final double size;
    public Square(Layer layer, Point topLeft, double size) {
        super(layer, topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
        this.size=size;
    }

    public double getSize(){
        return size;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }



}
