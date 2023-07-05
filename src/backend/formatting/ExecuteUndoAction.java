package backend.formatting;

public interface ExecuteUndoAction {
    
    private final LinkedList<Figure> undo = new LinkedList<>();
    private final LinkedList<Figure> redo = new LinkedList<>();


    public void addUndo(Figure figure){
        undo.add(figure);
    }

    public Figure undo(){
        return undo.pollLast();
    }

    public void addRedo(Figure figure){
        redo.add(figure);
    }

    public Figure redo(){
        return redo.pollLast();
    }

    public int sizeOfUndo(){
        return undo.size();
    }

    public int sizeOfRedo(){
        return redo.size();
    }

}
