package backend.model;

import backend.formatting.Move;
import frontend.drawing.Drawing;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure implements Drawing, Move {
    protected String figureName;
    private Color fillingColor;
    private Color borderColor;
    private double borderWidth;
    private Layer layer;
    public abstract void move(double diffX, double diffY);
    
    public abstract boolean pointBelongs(Point eventPoint);
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract void updateCoordinates(double diffX, double diffY);

    public Figure(Layer layer){
        this.layer = layer;
    }

    public Layer getLayer(){
        return this.layer;
    }
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
        this.borderColor = borderColor;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setFillingColor(Color fillingColor) {
        this.fillingColor = fillingColor;
    }

    public void formatFigure(Color borderColor, Color fillingColor, double width){
        setBorderColor(borderColor);
        setFillingColor(fillingColor);
        setBorderWidth(width);
    }

    public String getFigureName(){
        return figureName;
    }
}
