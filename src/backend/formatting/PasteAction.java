package backend.formatting;
import backend.CanvasState;
import backend.formatting.CopyFigure;
import backend.model.Figure;

public class PasteAction extends CopyFigure {


  public PasteAction(Figure figure, CanvasState canvasState){
    super(figure);
    setCanvasState(canvasState);
  }

  @Override
  public void activateAction() {
    canvasState.addFigure(getCopyFigure().getLayer(), getCopyFigure());
  }

  @Override
  public void undoAction(){
    DeleteAction delete = new DeleteAction(getCopyFigure(), canvasState);
    delete.activateAction(); 
  }

  @Override
  public String toString(){
    return "Paste %s".formatted(getCopyFigure().getFigureName());
  }
  
}
