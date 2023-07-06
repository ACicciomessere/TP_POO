package frontend.drawing;

import backend.model.Figure;
import backend.model.Layer;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;

public class DrawnSquare extends Square {

    private final GraphicsContext gc;
    private Layer layer;

    public DrawnSquare(Layer layer, Point topLeft, double size, GraphicsContext gc){
        super(layer, topLeft, size);
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
        Point topLeft = getTopLeft();
        double size= getSize()/2;
        DrawnSquare ret = new DrawnSquare(layer, topLeft, getSize(), gc);
        ret.move(figureCenter.getX()-topLeft.getX()-size, figureCenter.getY()-getTopLeft().getY()-size);
        return ret;
    }
}
