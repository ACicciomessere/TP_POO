package backend.model;

import backend.CanvasState;
import backend.formatting.ChangeBorderColor;
import backend.formatting.ChangeFillColor;
import backend.formatting.CopyFormat;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Figure {
    private final Layer layer;
    private Color fillingColor;
    private Color borderColor;
    private double borderWidth;
    private final CanvasState canvasState;
    public Figure(Layer layer, Color fillingColor, Color borderColor, double width, CanvasState canvasState) {
        this.layer = layer;
        this.fillingColor = fillingColor;
        this.borderColor = borderColor;
        this.borderWidth = width;
        this.canvasState = canvasState;
    }
    public Layer getLayer() {
        return this.layer;
    }

    public Color getFillingColor() {
        return fillingColor;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public abstract String getFigureType();

    public void setBorderColor(Color borderColor) {
        new ChangeBorderColor(this, borderColor, canvasState);
        this.borderColor = borderColor;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setFillingColor(Color fillingColor) {
        new ChangeFillColor(this, borderColor, canvasState);
        this.fillingColor = fillingColor;
    }

    public double getBorderWidth() {
        return this.borderWidth;
    }

    public boolean figureBelongs(Figure figure, Point eventPoint) {
        return figure.pointBelongs(eventPoint);
    }

    public void setFormatFigure(Point eventPoint, Color borderColor, Color fillingColor, double width) {
        new CopyFormat(canvasState, this, eventPoint, borderColor, fillingColor, width);
        this.fillingColor = fillingColor;
        this.borderWidth = width;
        this.borderColor = borderColor;
    }

    public abstract boolean pointBelongs(Point eventPoint);
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract void updateCoordinates(double diffX, double diffY);
}
