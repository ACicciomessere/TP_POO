package backend.model;

import backend.CanvasState;
import javafx.scene.paint.Color;

public class Circle extends Ellipse {
    private final double radius;

    public Circle(Layer layer, Point centerPoint, double radius, Color filling, Color borderColor, double width, CanvasState canvasState) {
        super(layer, centerPoint, 2 * radius, 2 * radius, filling, borderColor, width, canvasState);
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

    public double getRadius() {
        return radius;
    }

    public String getFigureType() {
        return "Circulo";
    }
}
