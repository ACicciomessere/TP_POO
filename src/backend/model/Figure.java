package backend.model;

import frontend.drawing.Drawing;
import javafx.scene.canvas.GraphicsContext;

public abstract class Figure implements Drawing {
    protected String figureName;
    public abstract boolean pointBelongs(Point eventPoint);
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    public abstract void updateCoordinates(double diffX, double diffY);

    public String getFigureName(){
        return figureName;
    }
}
