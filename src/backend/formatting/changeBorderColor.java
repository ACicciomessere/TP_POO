package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;

import javafx.scene.paint.Color;

public class changeBorderColor extends ActionForFormat {
    private final Color previousColor;
    private final Color nextColor;

    public changeBorderColor(Figure figure, Color currentColor, Color nextColor, CanvasState canvasState) {
        super(figure, currentColor, canvasState);
        this.previousColor = currentColor;
        this.nextColor = nextColor;
        activateAction();  //cambio de color
    }

    @Override
    public void undoAction() {
        changeColor(previousColor);
    }

    @Override
    public void activateAction(){
        manipulableFigure.setBorderColor(nextColor);
        CanvasState.findFigure(manipulableFigure).setBorderColor(nextColor);
    }

    @Override
    public void changeColor(Color color){
        for( Figure figure : canvasState.figures()){
            if(figure == manipulableFigure){
                figure.setBorderColor(color);
            }
        }
    }

    @Override
    public String toString() {
        return "%s border".formatted(manipulableFigure.getFigureName());
    }
}
