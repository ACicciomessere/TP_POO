package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.paint.Color;

public class ChangeFillColor extends ActionForFormat{

    public ChangeFillColor(Figure selection, Color filling, CanvasState canvasState){
        super(selection, filling, canvasState);
    }


    @Override
    public void undoAction() {
        changeColor(previousColor);
    }

    @Override
    public void activateAction(){
        previousColor = manipulableFigure.getFillingColor();
        super.activateAction();
    }

    @Override
    public void changeColor(Color color){
        for(Figure figure : canvasState.figures()){
            if( figure == manipulableFigure){
                figure.setFillingColor(color);
            }
        }
    }

    @Override
    public String toString() {
        return "%s filling".formatted(manipulableFigure.getFigureName());
    }
}
