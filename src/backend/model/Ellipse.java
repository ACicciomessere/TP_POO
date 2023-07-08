
package backend.model;

        import javafx.scene.canvas.GraphicsContext;

public abstract class Ellipse extends Figure {

    private Point centerPoint;
    private final double sMayorAxis, sMinorAxis;

    public Ellipse(Layer layer, Point centerPoint, double sMayorAxis, double sMinorAxis) {
        super(layer);
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    public void move(double diffX, double diffY){
        this.centerPoint= new Point (centerPoint.getX()+diffX, centerPoint.getY()+diffY);
    }
    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public boolean pointBelongs(Point eventPoint) {
        return ((Math.pow(eventPoint.getX() - getCenterPoint().getX(), 2) / Math.pow(getsMayorAxis(), 2)) +
                (Math.pow(eventPoint.getY() - getCenterPoint().getY(), 2) / Math.pow(getsMinorAxis(), 2))) <= 0.30;
    }

    @Override
    public void fill(GraphicsContext gc) {
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
    }

    @Override
    public void stroke(GraphicsContext gc) {
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
    }

    @Override
    public void updateCoordinates(double diffX, double diffY) {
        getCenterPoint().changeX(diffX);
        getCenterPoint().changeY(diffY);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

}

