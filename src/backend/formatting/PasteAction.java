public class PasteAction extends CopyFigure{
  
  private Figure copyedFigure;
  private CanvasState canvasState;
  private DeleteAction delete;

  public PasteAction(Figure figure, CanvasState canvasState){
    this.copyedFigure = figure;
    this.canvasState = canvasState;
    this.delete = new DeleteAction(copyedFigure, canvasState);
  }

  @Override
  public void activateAction() {
    canvasState.addFigure(figure.getLayer(), figure);
  }

  @Override
  public void undoAction(){
    delete.activateAction(); 
  }
  
}
