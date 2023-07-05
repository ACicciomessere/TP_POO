package backend.formatting;

import backend.CanvasState;
import backend.model.Point;
import frontend.drawing.DrawnRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MakeActionForSquare extends MakeActionForFigure{

    public MakeActionForSquare(Point start, MouseEvent event, GraphicsContext gc, CanvasState canvasState, Color borderColor, Color fillingColor, double width){
        super(canvasState);
        double size = Math.abs(event.getX() - start.getX());
        setManipulableFigure(new DrawnRectangle(layer, start, new Point(event.getX(), event.getY()), gc));
        manipulableFigure.formatFigure(borderColor, fillingColor, width);
    }

    @Override
    public String toString(){
        return super.toString() + "Square";
    }
}
