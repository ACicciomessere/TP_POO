package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.paint.Color;

public abstract class ActionForFormat extends ActionAbsImpl{
    protected Color color;
    protected Color previousColor;

    ActionForFormat(Figure selection, Color color, CanvasState canvasState){
        setCanvasState(canvasState);
        this.color=color;
        setManipulableFigure(selection);
    }

    public abstract void changeColor(Color color);

    @Override
    public void activateAction(){

    }
}
