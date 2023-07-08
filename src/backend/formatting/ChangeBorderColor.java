package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;

import javafx.scene.paint.Color;

public class ChangeBorderColor extends ActionForFormat {

    public ChangeBorderColor(Figure figure, Color currentColor, Color nextColor, CanvasState canvasState) {
        super(figure, currentColor, canvasState);
    }

    @Override
    public void undoAction() {
        changeColor(previousColor);
    }

    @Override
    public void activateAction(){
        previousColor=figure.getBorderColor();
        super.activateAction();
    }

    @Override
    public void changeColor(Color color){
        for( Figure selection : canvasState.figures()){
            if(selection == figure){
                selection.setBorderColor(color);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString()+"%s border".formatted(figure.getFigureName());
    }
}
