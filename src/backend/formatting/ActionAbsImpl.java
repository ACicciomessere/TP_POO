package backend.formatting;

import backend.CanvasState;
import backend.formatting.Action;
import backend.model.Figure;

public abstract class ActionAbsImpl implements Action {
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
