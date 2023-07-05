package backend.model;

public abstract class Circle extends Ellipse {
    private final double radius;
    private static final String TYPE = "Circle";
    public Circle(String layer, Point centerPoint, double radius) {
        super(layer, centerPoint, 2 * radius, 2 * radius); // Revisar si esto esta bien
        this.radius = radius;
        this.figureName=TYPE;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), radius);
    }

    @Override
    public boolean pointBelongs(Point eventPoint) {
        return Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(getCenterPoint().getY() - eventPoint.getY(), 2)) < getRadius();
    }

    public double getRadius() {
        return radius;
    }
}
