package backend.formatting;

import backend.model.Figure;
import backend.model.Point;

public class CopyFigure extends ActionAbsImpl {

    private Figure copyFigure;
    private final Point canvasCenter= new Point(400, 300);

    public CopyFigure(Figure selection){
        setManipulableFigure(selection);
    }

    public Figure getCopyFigure() {
        return copyFigure;
    }

    @Override
    public void activateAction() {
        this.copyFigure=manipulableFigure.duplicateFigure(canvasCenter);
    }

    @Override
    public void undoAction() {
        this.copyFigure=null;
    }

    @Override
    public String toString() {
        return "Se copia %s".formatted(manipulableFigure.getFigureName());
    }
}
