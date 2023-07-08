package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;

public class DeleteFigure extends ActionImpl {
  public DeleteFigure(CanvasState canvasState, Figure figure) {
    super(canvasState, figure);
  }

  @Override
  public void undoAction() {
    getCanvasState().addFigure(getFigure().getLayer(), getFigure());
  }

  @Override
  public void activateAction() {
    getCanvasState().deleteFigure(getFigure());
  }

  @Override
  public String toString(){
      return String.format("Borrar %s", getFigure().getFigureType());
    }
}
