public class CutAction extends CopyFigure{
  private DeleteAction delete;

  public CutAction(Figure figure, CanvasState canvasState){
    setCanvasState(canvasState);
    super(figure);
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
