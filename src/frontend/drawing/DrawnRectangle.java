package frontend.drawing;

import backend.model.Figure;
import backend.model.Layer;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

public class DrawnRectangle extends Rectangle implements Drawing {

    private final GraphicsContext gc;
    private Layer layer;
    public DrawnRectangle(Layer layer, Point topLeft, Point bottomRight, GraphicsContext gc){
        super(layer, topLeft, bottomRight);
        this.gc=gc;
    }

    @Override
    public void draw(){
        Point topLeft = getTopLeft();
        Point bottomRight = getBottomRight();
        gc.fillRect(topLeft.getX(), topLeft.getY(), Math.abs(topLeft.xDistance(bottomRight)), Math.abs(topLeft.yDistance(bottomRight)));
        gc.strokeRect(topLeft.getX(), topLeft.getY(), Math.abs(topLeft.xDistance(bottomRight)), Math.abs(topLeft.yDistance(bottomRight)));
    }

    @Override
    public Figure duplicateFigure(Point figureCenter) {
        double halfWidth = getBottomRight().xDistance(getTopLeft())/2;
        double halfHeight = getTopLeft().yDistance((getBottomRight()))/2;

        Point topLeft = new Point(figureCenter.getX() - halfWidth, figureCenter.getY() - halfHeight);
        Point bottomRight = new Point(figureCenter.getX() + halfWidth, figureCenter.getY() + halfHeight);
        return new DrawnRectangle(layer, topLeft, bottomRight, gc);
    }
}
