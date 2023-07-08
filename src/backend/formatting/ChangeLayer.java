package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Layer;


public class ChangeLayer extends ActionImpl{

    private Layer previousLayer;
    private final Layer newLayer;

    public ChangeLayer(CanvasState canvasState, Figure figure, Layer layer) {
        super(canvasState, figure);
        this.newLayer=layer;
    }

    public void changeLayer(Layer layer) {
        Figure figure=getFigure();
        previousLayer=figure.getLayer();
        getCanvasState().deleteFigure(figure);
        figure.setLayer(layer);
        getCanvasState().addFigure(figure.getLayer(), figure);
    }
    @Override
    public void undoAction() {
        changeLayer(previousLayer);
    }

    @Override
    public void activateAction() {
        System.out.println(getFigure().getLayer().getName());
        changeLayer(newLayer);
        System.out.println(getFigure().getLayer().getName());
    }

    @Override
    public String toString(){
        return "Cambio de (%s) a (%s)".formatted(previousLayer, newLayer);
    }
}
