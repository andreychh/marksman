package marksman.client.lobby.player;

import javafx.beans.property.Property;

public final class Player {
    private final Property<String> nameProperty;
    private final Property<Boolean> readinessProperty;

    public Player(final Property<String> nameProperty, final Property<Boolean> readinessProperty) {
        this.nameProperty = nameProperty;
        this.readinessProperty = readinessProperty;
    }

    public Property<String> nameProperty() {
        return nameProperty;
    }

    public Property<Boolean> readinessProperty() {
        return readinessProperty;
    }
}
