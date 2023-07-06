package backend.formatting;

import backend.CanvasState;
import backend.formatting.ActionAbsImpl;
import backend.model.Figure;

public class DeleteAction extends ActionAbsImpl {
  private Figure figure;
  private CanvasState canvasState;

  public DeleteAction(Figure figure, CanvasState canvasState) {
    this.figure = figure;
    this.canvasState = canvasState;
  }
  
  @Override
  public void undoAction(){
    canvasState.addFigure(figure.getLayer(), figure);
  }

  @Override
  public void activateAction(){
    canvasState.deleteFigure(figure);
  }

  @Override
  public String toString() {
    return "Delete %s".formatted(manipulableFigure.getFigureName());
  }
}
