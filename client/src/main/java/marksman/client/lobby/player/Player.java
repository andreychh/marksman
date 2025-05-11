package marksman.client.lobby.player;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import marksman.shared.network.Connection;
import marksman.shared.network.Message;
import marksman.shared.network.MessageHandler;

public final class Player implements MessageHandler {
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

    @Override
    public void handleMessage(final Message message, final Connection connection) {
        switch (message.value("action")) {
            case "user.readinessChanged" -> {
                this.readinessProperty.set(Boolean.parseBoolean(message.value("user.readiness")));
            }
            default -> {
                throw new RuntimeException("Unknown action: " + message.value("action"));
            }
        }
    }
}
