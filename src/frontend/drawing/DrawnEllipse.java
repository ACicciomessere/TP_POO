package frontend.drawing;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawnEllipse extends Ellipse {

    private final GraphicsContext gc;

    public DrawnEllipse(Point center, double sMayorAxis, double sMinorAxis, GraphicsContext gc){
        super(center, sMayorAxis, sMinorAxis);
        this.gc=gc;
    }

    @Override
    public void draw(){
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis()/2), getCenterPoint().getY() -(getsMinorAxis()/2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis()/2), getCenterPoint().getY() -(getsMinorAxis()/2), getsMayorAxis(), getsMinorAxis());
    }

    @Override
    public Figure duplicateFigure(Point figureCenter) {
        return new DrawnEllipse(figureCenter, getsMayorAxis(), getsMinorAxis(), gc);
    }

}