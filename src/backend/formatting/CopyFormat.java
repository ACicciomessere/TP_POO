package backend.formatting;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class CopyFormat extends ActionImpl {
    private final Color newBorderColor;
    private final Color newFillingColor;
    private final double newBorderWidth;
    private final Point eventPoint;
    private Color previousBorderColor;
    private Color previousFillingColor;
    private double previousBorderWidth;
    private Figure auxFigure;

    public CopyFormat(CanvasState canvasState, Figure figure, Point eventPoint, Color newBorderColor, Color newFillingColor, double newBorderWidth) {
        super(canvasState, figure);
        for(Figure f : getCanvasState().figures()) {
            if(f.pointBelongs(eventPoint)) {
                this.auxFigure = f;
            }
        }
        this.newBorderColor = newBorderColor;
        this.newFillingColor = newFillingColor;
        this.newBorderWidth = newBorderWidth;
        this.eventPoint = eventPoint;
    }

    @Override
    public void undoAction() {
        getCanvasState().findFigure(auxFigure).setFormatFigure(eventPoint, previousBorderColor, previousFillingColor, previousBorderWidth);
    }

    @Override
    public void activateAction() {
        this.previousBorderColor = auxFigure.getBorderColor();
        this.previousBorderWidth = auxFigure.getBorderWidth();
        this.previousFillingColor = auxFigure.getFillingColor();
        auxFigure.setFormatFigure(eventPoint, newBorderColor, newFillingColor, newBorderWidth);
    }

    @Override
    public String toString() {
        return String.format("Copiar Formato de %s", getFigure().getFigureType());
    }
}
