package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;

public abstract class ActionImpl implements Action {
  private final Figure figure;
  private final CanvasState canvasState;

  public ActionImpl(CanvasState canvasState, Figure figure) {
    this.canvasState = canvasState;
    this.figure = figure;
  }

  public CanvasState getCanvasState() {
    return canvasState;
  }

  public Figure getFigure() {
    return figure;
  }

}
