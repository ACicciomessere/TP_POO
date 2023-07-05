package backend.formatting;

import backend.model.Figure;

import java.awt.*;

public class changeBorderColor implements Action {
    private final Figure figure;
    private final Color previousColor;

    public changeBorderColor(Figure figure, Color previousColor) {
        this.figure = figure;
        this.previousColor = previousColor;
    }

    @Override
    public void undo() {
        figure.changeBorderColor(previousColor); // No se si figure implementa el metodo de changeBorderColor
        CanvasState.findFigure(figure).changeBorderColor(previousColor);
    }
}
