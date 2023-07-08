package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;

public class DrawFigure extends ActionImpl {
    public DrawFigure(CanvasState canvasState, Figure figure) {
        super(canvasState, figure);
    }

    @Override
    public void activateAction() {
        getCanvasState().addFigure(getFigure().getLayer(), getFigure());
    }

    @Override
    public void undoAction() {
        getCanvasState().deleteFigure(getFigure());
    }

    @Override
    public String toString(){
        return String.format("Borrar %s", getFigure().getFigureType());
    }
}
