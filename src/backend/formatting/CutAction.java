package backend.formatting;

import backend.CanvasState;
import backend.formatting.CopyFigure;
import backend.formatting.DeleteAction;
import backend.model.Figure;

public class CutAction extends CopyFigure {
  private DeleteAction delete;

  public CutAction(Figure figure, CanvasState canvasState){
    super(figure);
    setCanvasState(canvasState);
    this.delete = new DeleteAction(figure, canvasState);
  }
  
  @Override
  public void activateAction() {
    super.activateAction();
    delete.activateAction();
   }

  @Override
  public void undoAction() {
    super.undoAction();
    delete.undoAction();
  }

  @Override
  public String toString() {
      return "Cutting %s".formatted(manipulableFigure.getFigureName());
  }
}
