public class DeleteAction extends ActionAbsImpl{
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
}
