package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.paint.Color;

public class ChangeFillColor extends ChangeColor {
    public ChangeFillColor(Figure figure, Color newColor, CanvasState canvasState){
        super(figure, figure.getFillingColor(), newColor, canvasState);
    }

    @Override
    public void changeColor(Color color) {
        getCanvasState().findFigure(getFigure()).setFillingColor(color);
    }

    @Override
    public Color getPreviousColor() {
        return getFigure().getFillingColor();
    }

    @Override
    public String toString() {
        return String.format("%s relleno de %s", super.toString(), getFigure().getFigureType());
    }
}
