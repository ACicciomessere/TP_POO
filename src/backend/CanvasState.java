package backend;

import backend.model.Figure;
import backend.model.Layer;

import java.util.*;

public class CanvasState {
    private final Set<Layer> checkedLayers = new TreeSet<>(); // Coleccion para almacenar las layers
    private final Map<Layer, List<Figure>> layersFigures = new HashMap<>(); // El key es la layer, y el value son las figures de la layer

    public void addFigure(Layer layer, Figure figure) {
        if(layer == null) {
            throw new IllegalArgumentException("Selecciona una capa"); // Aca hay que poner un alert de seleccionar una capa
        } // En principio esto se puede sacar, pues no habria que validarlo
        layersFigures.get(layer).add(figure);
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

