package backend.formatting;

import backend.CanvasState;
import backend.formatting.Action;
import backend.model.Figure;

public abstract class ActionAbsImpl implements Action {
  protected Figure figure;
  protected CanvasState canvasState;

  public ActionAbsImpl(CanvasState canvasState, Figure figure) {
    this.canvasState = canvasState;
    this.figure = figure;
  }

  public CanvasState getCanvasState() {
    return canvasState;
  }

  public Figure getFigure() {
    return figure;
  }
  public void setFigure(Figure figure){
    this.figure = figure;
  }

  
}
