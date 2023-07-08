package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;

import javafx.scene.paint.Color;

public class ChangeBorderColor extends ChangeColor {
    public ChangeBorderColor(Figure figure, Color newColor, CanvasState canvasState){
        super(figure, figure.getBorderColor(), newColor, canvasState);
    }

    @Override
    public void changeColor(Color color) {
        getCanvasState().findFigure(getFigure()).setBorderColor(color);
    }

    @Override
    public Color getPreviousColor() {
        return getFigure().getBorderColor();
    }

    @Override
    public String toString() {
        return String.format("%s del borde de %s", super.toString(), getFigure().getFigureType());
    }
}
