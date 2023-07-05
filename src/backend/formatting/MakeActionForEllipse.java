package backend.formatting;

import backend.CanvasState;
import backend.model.Point;
import frontend.drawing.DrawnEllipse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MakeActionForEllipse extends MakeActionForFigure{
    public MakeActionForEllipse(Point start, MouseEvent event, GraphicsContext gc, CanvasState canvasState, Color borderColor, Color fillingColor, double width){
        super(canvasState);
        Point center = new Point ((Math.abs(event.getX() + start.getX()))/2, (Math.abs((event.getY() + start.getY())))/2);
        double sMayorAxis = Math.abs(event.getX() - start.getX());
        double sMinorAxis = Math.abs(event.getY() - start.getY());
        setManipulableFigure(new DrawnEllipse(center, sMayorAxis, sMinorAxis, gc));
        manipulableFigure.formatFigure(borderColor, fillingColor, width);
    }

    @Override
    public String toString(){
        return super.toString() + "Ellipse";
    }
}
