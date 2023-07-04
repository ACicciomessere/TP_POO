package backend.model;

import frontend.drawing.Drawing;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure implements Drawing {
    protected String figureName;
    private Color fillingColor;
    private Color borderColor;
    private double borderWidth;
    public abstract boolean pointBelongs(Point eventPoint);
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract void updateCoordinates(double diffX, double diffY);
    public abstract Figure selected(Point startPoint, Point endPoint); //Para mejorar el codigo de PaintPane con los else if

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
