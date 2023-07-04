package backend.model;

public class Circle extends Ellipse {
    private final double radius;

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2 * radius, 2 * radius); // Revisar si esto esta bien
        this.radius = radius;
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

    @Override
     public Figure selected(Point startPoint, Point endPoint){
         double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
		 return new Circle(startPoint, circleRadius);
     }

    public double getRadius() {
        return radius;
    }
}
