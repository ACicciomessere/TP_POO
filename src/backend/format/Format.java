package backend.format;

import backend.CanvasState;
import backend.model.Figure;

public abstract class Format implements ExecuteUndoAction {
    protected Figure manipulableFigure;
    protected CanvasState canvasState;

    public void setCanvasState(CanvasState canvasState){
        this.canvasState=canvasState;
    }

    public void setManipulableFigure(Figure manipulableFigure) {
        this.manipulableFigure = manipulableFigure;
    }

    public abstract String toString();
}
