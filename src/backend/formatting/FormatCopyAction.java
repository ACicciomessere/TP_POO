package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class FormatCopyAction extends ActionAbsImpl{

    private final Color newBorderColor;
    private final Color newFillingColor;
    private Color oldBorderColor;
    private Color oldFillingColor;
    private final Point point;
    private double newBorderWidth;
    private double oldBorderWidth;
    private Figure aux;

    public FormatCopyAction(Figure selection, CanvasState canvasState, Point point, Color newBorderColor, Color newFillingColor, double newBorderWidth){
        setCanvasState(canvasState);
        setManipulableFigure(selection);
        this.newBorderColor=newBorderColor;
        this.newFillingColor=newFillingColor;
        this.newBorderWidth=newBorderWidth;
        this.point=point;
    }

    public void undoAction() {
        for(Figure figure : canvasState.figures()){
            if(figure== aux){
                figure.formatFigure(oldBorderColor, oldFillingColor, oldBorderWidth);
            }
        }
    }

    public void activateAction(){
        Iterator<Figure> iterator = canvasState.figures().iterator();
        while (iterator.hasNext()){
            Figure figure = iterator.next();
            if(figure.pointBelongs(point)){
                aux= figure;
                oldBorderColor=figure.getBorderColor();
                oldFillingColor=figure.getFillingColor();
                oldBorderWidth=figure.getBorderWidth();
                figure.formatFigure(newBorderColor, newFillingColor, newBorderWidth);
                return;
            }
        }
    }


    @Override
    public String toString() {
        return "Copying %s format".formatted(manipulableFigure.getFigureName());
    }
}
