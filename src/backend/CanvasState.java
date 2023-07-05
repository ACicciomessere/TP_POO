package backend;

import backend.model.Figure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanvasState {

    private final List<Figure> list = new ArrayList<>();

    private Map<String, List<Figure>> layersFigures = new HashMap<>(); // El key es la layer, y el value son las figures de la layer

    public void addFigure(String layer, Figure figure) {
        if(layer == null) {
            throw new IllegalArgumentException("Selecciona una capa"); // Aca hay que poner un alert de seleccionar una capa
        }
        if(!layersFigures.containsKey(layer)) {
            layersFigures.put(layer, new ArrayList<>());
        }
        layersFigures.get(layer).add(figure);
        list.add(figure);
    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
    }

    public Iterable<Figure> figures(List<String> layers) {
        List<Figure> toReturn = new ArrayList<>();
        for(String layer : layers) {
            if(layersFigures.containsKey(layer)) {
                toReturn.addAll(layersFigures.get(layer));
            }
        }
        return toReturn;
    }
}
