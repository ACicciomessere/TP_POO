package backend.formatting;

import backend.CanvasState;
import backend.model.Layer;

public abstract class MakeActionForFigure extends ActionAbsImpl{
    public Layer layer;

    MakeActionForFigure(CanvasState canvasState){
        setCanvasState(canvasState);
    }

    public void activateAction(){
        canvasState.addFigure(layer, manipulableFigure);
    }

    public void undoAction() {
        canvasState.deleteFigure(manipulableFigure);
    }

    @Override
    public String toString(){
        return "Draw";
    }
}
