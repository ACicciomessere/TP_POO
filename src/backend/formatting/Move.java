package backend.formatting;

@FunctionalInterface
public interface Move {
    void move(double diffX, double diffY);
}
