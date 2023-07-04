package backend.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Figure {
    public abstract boolean pointBelongs(Point eventPoint);
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
}
