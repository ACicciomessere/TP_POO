package frontend.drawing;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Layer;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawnCircle extends Circle {
    private Layer layer;
    private final GraphicsContext gc;

    public DrawnCircle(Layer layer, Point center, double radius, GraphicsContext gc){
        super(layer, center, radius);
        this.gc=gc;
    }
    @Override
    public void draw() {
        double diameter = getRadius()*2;
        gc.fillOval(getCenterPoint().getX() - getRadius(), getCenterPoint().getY()-getRadius(), diameter, diameter);
        gc.strokeOval(getCenterPoint().getX() - getRadius(), getCenterPoint().getY()-getRadius(), diameter, diameter);
    }

    @Override
    public Figure duplicateFigure(Point figureCenter) {
        return new DrawnCircle(layer, figureCenter, getRadius(), gc);
    }
}
