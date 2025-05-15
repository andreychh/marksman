package marksman.client.lobby.player;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import marksman.shared.network.Connection;
import marksman.shared.network.MessageReceiver;
import marksman.shared.network.ReceivedMessage;

public final class Player implements MessageReceiver {
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
    public void receiveMessage(final ReceivedMessage message, final Connection connection) {
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
