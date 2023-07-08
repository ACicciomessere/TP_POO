package backend;

import backend.formatting.Action;
import backend.model.Figure;
import backend.model.Layer;

import java.util.*;

public class CanvasState {
    private final Set<Layer> checkedLayers = new TreeSet<>(); // Coleccion para almacenar las layers
    private final Map<Layer, List<Figure>> layersFigures = new HashMap<>(); // El key es la layer, y el value son las figures de la layer

    private final Deque<Action> undo = new ArrayDeque<>();
    private final Deque<Action> redo = new ArrayDeque<>();

    public void addFigure(Layer layer, Figure figure) {
        if(layer == null) {
            throw new IllegalArgumentException("Seleccione una capa");
        }
        layersFigures.get(layer).add(figure);
    }

    public boolean isRedoAvailable(){
        return 0 < redo.size();
    }

    public void addUndo(Action action) {
        if(isRedoAvailable()) {
            redo.clear();
        }
        undo.push(action);
    }

    public boolean isUndoAvailable(){
        return undo.size() > 0;
    }

    public Action getNextUndo() {
        if (isUndoAvailable()){
            return undo.peek();
        }
        return null;
    }

    public Action getNextRedo() {
        if (isRedoAvailable()){
            return redo.peek();
        }
        return null;
    }

    public boolean undoLastAction() {
        if(isUndoAvailable()) {
            Action action = undo.pop();
            action.undoAction();
            redo.push(action);
            return true;
        }
        return false;
    }

    public boolean redoLastAction() {
        if(isRedoAvailable()){
            Action action = redo.pop();
            action.activateAction();
            undo.push(action);
            return true;
        }
        return false;
    }

    public int getRedoAvailable() {
        return redo.size();
    }

    public int getUndoAvailable() {
        return undo.size();
    }

    public Layer addCheckedLayer() {
        Layer aux = new Layer();
        checkedLayers.add(aux);
        layersFigures.put(aux, new ArrayList<>());
        return aux;
    }

    public void deleteFigure(Figure figure) {
        layersFigures.get(figure.getLayer()).remove(figure);
    }

    public void activateLayer(Layer layer) {
        if(checkedLayers.contains(layer)) {
            layer.activate();
        }
    }

    public void deactivateLayer(Layer layer) {
        if(checkedLayers.contains(layer)) {
            layer.deactivate();
        }
    }

    public Figure findFigure(Figure figure){
        for(Layer layer : layersFigures.keySet()){
            for(int i = 0; i < layersFigures.get(layer).size(); i++){
                if(layersFigures.get(layer).get(i).equals(figure)){
                    return figure;
                }
            }
        }
        throw new NoSuchElementException("Figure not found");
    }

    public Iterable<Figure> figures() {
        List<Figure> toReturn = new ArrayList<>();
        for(Layer layer : checkedLayers) {
            if(layer.isActivated()) {
                toReturn.addAll(layersFigures.get(layer));
            }
        }
        return toReturn;
    }

}


