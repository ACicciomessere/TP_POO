package backend.model;

import backend.CanvasState;
import backend.formatting.ChangeBorderColor;
import backend.formatting.ChangeFillColor;
import backend.formatting.CopyFormat;
import frontend.drawing.Drawing;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public abstract class Figure{
    protected String figureName;
    private Color fillingColor;
    private Color borderColor;
    private double borderWidth;
    private Layer layer;
    private CanvasState canvasState;
    public Figure(Layer layer, Color fillingColor, Color borderColor, double width, CanvasState canvasState) {
        this.layer = layer;
        this.fillingColor = fillingColor;
        this.borderColor = borderColor;
        this.borderWidth = width;
        this.canvasState = canvasState;
    }
    public abstract void move(double diffX, double diffY);
    
    public abstract boolean pointBelongs(Point eventPoint);
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract void updateCoordinates(double diffX, double diffY);

    public Layer getLayer(){
        return this.layer;
    }
    public abstract String getFigureType();
    public Color getBorderColor() {
        return borderColor;
    }

    public Color getFillingColor() {
        return fillingColor;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

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

    public void setFormatFigure(Point eventPoint, Color borderColor, Color fillingColor, double width){
        new CopyFormat(canvasState, this, eventPoint, borderColor, fillingColor, width);
        this.fillingColor = fillingColor;
        this.borderWidth = width;
        this.borderColor = borderColor;
    }

    @Override
    public boolean equals(Object o){
        if( o == this){
            return true;
        }
        if(o instanceof Figure){
            return ((Figure) o).getLayer()==layer && Objects.equals(((Figure) o).getFigureName(), figureName) && ((Figure) o).getBorderColor()==borderColor && ((Figure) o).getFillingColor()==fillingColor && ((Figure) o).getBorderWidth()==borderWidth;
        }
        return false;
    }

    public String getFigureName(){
        return figureName;
    }
}
