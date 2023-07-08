package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.paint.Color;

public abstract class ChangeColor extends ActionImpl {
    private final Color newColor;
    private Color previousColor;

    ChangeColor(Figure selection, Color previousColor, Color newColor, CanvasState canvasState){
        super(canvasState, selection);
        this.newColor=newColor;
        this.previousColor=previousColor;
    }

    public abstract void changeColor(Color color);
    public abstract Color getPreviousColor();

    @Override
    public void activateAction(){
        previousColor = getPreviousColor();
        changeColor(newColor);
    }

    @Override
    public void undoAction() {
        changeColor(previousColor);
    }

    @Override
    public String toString() {
        return "Cambiar color";
    }
}
