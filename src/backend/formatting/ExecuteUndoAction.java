package backend.formatting;

public abstract class ExecuteUndoAction {

    private final LinkedList<Action> undo = new LinkedList<>();
    private final LinkedList<Action> redo = new LinkedList<>();

    
    public void addUndo(Action action){
        undo.add(action);
    }

    public Figure undo(){
        return undo.pollLast();
    }

    public void addRedo(Action action){
        redo.add(action);
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

    public abstract void activateAction();
    
    public abstract void undoAction();
    
}
