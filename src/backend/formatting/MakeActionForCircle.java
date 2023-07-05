package backend.formatting;

import backend.CanvasState;
import backend.model.Point;
import frontend.drawing.DrawnCircle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MakeActionForCircle extends MakeActionForFigure{

    public MakeActionForCircle(Point start, MouseEvent event, GraphicsContext gc, CanvasState canvasState, Color borderColor, Color fillingColor, double width){
        super(canvasState);
        double radius = Math.abs(event.getX() - start.getX());
        setManipulableFigure(new DrawnCircle(layer, start, radius, gc));
        manipulableFigure.formatFigure(borderColor, fillingColor, width);
    }

    @Override
    public String toString() {
        return super.toString() + "Circle";
    }
}
