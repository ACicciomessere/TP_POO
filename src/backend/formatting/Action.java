package backend.formatting;

import backend.model.Figure;

public interface Action {
    void undoAction();
    void activateAction();

}
