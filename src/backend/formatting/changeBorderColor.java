package backend.formatting;

import backend.model.Figure;

import java.awt.*;

public class changeBorderColor implements Action {
    private final Color previousColor;
    private final Color nextColor;

    public changeBorderColor(Figure figure, Color currentColor, Color nextColor) {
        super(figure);
        this.previousColor = previousColor;
        this.nextColor = nextColor;
        activateAction();  //cambio de color
    }

    @Override
    public void undoAction() {
        figure.setBorderColor(previousColor); // No se si figure implementa el metodo de changeBorderColor
        CanvasState.findFigure(figure).setBorderColor(previousColor);
    }

    @Override
    public void activateAction(){
        figure.setBorderColor(nextColor);
        CanvasState.findFigure(figure).setBorderColor(nextColor);
    }
}
