package backend.model;

public class Point {

   private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double xDistance(Point other){
        return other.getX()-x;
    }

    public double yDistance(Point other){
        return other.getY()-x;
    }

    @Override
    public boolean equals(Object o){
        if( o == this){
            return true;
        }
        if(o instanceof Point){
            return ((Point) o).getX()==x && ((Point) o).getY()==y;
        }
        return false;
    }

   public void changeX(double x) {
        this.x += x;
    }

    public void changeY(double y) {
        this.y += y;
    }
   
    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

}
