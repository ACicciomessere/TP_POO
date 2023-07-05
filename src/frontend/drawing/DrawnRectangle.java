package frontend.drawing;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

public class DrawnRectangle extends Rectangle implements Drawing {

    private final GraphicsContext gc;

    public DrawnRectangle(Point topLeft, Point bottomRight, GraphicsContext gc){
        super(topLeft, bottomRight);
        this.gc=gc;
    }

    @Override
    public void draw(){
        Point topLeft = getTopLeft();
        Point bottomRight = getBot;
    }
}
