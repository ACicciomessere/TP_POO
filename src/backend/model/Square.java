package backend.model;

import backend.CanvasState;
import javafx.scene.paint.Color;

public class Square extends Rectangle {
    public Square(Layer layer, Point topLeft, double size, Color filling, Color borderColor, double width, CanvasState canvasState) {
        super(layer, topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size), filling, borderColor, width, canvasState);
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    public String getFigureType() {
        return "Cuadrado";
    }
}

