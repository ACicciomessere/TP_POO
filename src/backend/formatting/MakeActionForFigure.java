package backend.formatting;

import backend.CanvasState;

public abstract class MakeActionForFigure extends FormattingActions{

    MakeActionForFigure(CanvasState canvasState){
        setCanvasState(canvasState);
    }

    public void activateAction(){
        canvasState.addFigure(manipulableFigure);
    }

    public void undoAction() {
        canvasState.deleteFigure(manipulableFigure);
    }

    @Override
    public String toString(){
        return "Draw";
    }
}
