package backend.formatting;

import backend.CanvasState;
import backend.model.Layer;

public abstract class MakeActionForFigure extends ActionAbsImpl{
    protected Layer layer;

    public Layer getLayer(){
        return layer;
    }

    public void setLayer(Layer layer){
        this.layer=layer;
    }

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
