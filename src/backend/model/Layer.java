package backend.model;

import java.util.Objects;

public class Layer implements Comparable<Layer> {
    private static final int INITIAL_LAYER = 1;
    private static int CURRENT_LAYER = INITIAL_LAYER;
    private final String name;
    private boolean activated = true;

    public Layer() {
        this.name = "Layer " + CURRENT_LAYER++;
    }

    public void activate() {
        activated = true;
    }

    public void deactivate() {
        activated = false;
    }

    public String getName() {
        return this.name;
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public int compareTo(Layer o2) {
        return this.name.compareTo(o2.name);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof Layer layer)) {
            return false;
        }
        return this.name.equals(layer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return this.name;
    }
}

