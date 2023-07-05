package frontend.drawing;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawnCircle extends Circle {

    private final GraphicsContext gc;

    public DrawnCircle(Point center, double radius, GraphicsContext gc){
        super(center, radius);
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
        return new DrawnCircle(figureCenter, getRadius(), gc);
    }
}
