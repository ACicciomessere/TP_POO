package frontend.drawing;

import backend.model.Figure;
import backend.model.Point;

public interface Drawing {

    void draw();

    Figure duplicateFigure(Point figureCenter);
}
