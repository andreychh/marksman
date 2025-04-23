package marksman.client.lobby.player;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public final class Player {
    private final StringProperty nameProperty;
    private final BooleanProperty readinessProperty;

    public Player(final StringProperty nameProperty, final BooleanProperty readinessProperty) {
        this.nameProperty = nameProperty;
        this.readinessProperty = readinessProperty;
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public BooleanProperty readinessProperty() {
        return readinessProperty;
    }
}
