public abstract class ActionAbsImpl includes Action{
  private Figure figure;

  public Figure(Figure figure){
    this.figure = figure;
  }
  
  public abstract void undoAction();
  public abstract void activateAction();
  
}
