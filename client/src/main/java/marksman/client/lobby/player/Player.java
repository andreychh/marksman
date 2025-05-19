package marksman.client.lobby.player;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.MessageReceiver;
import marksman.shared.network.messaging.ReceivedMessage;

public final class Player implements MessageReceiver {
    private final StringProperty nameProperty;
    private final BooleanProperty readinessProperty;

    public Player(final StringProperty nameProperty, final BooleanProperty readinessProperty) {
        this.nameProperty = nameProperty;
        this.readinessProperty = readinessProperty;
    }

    public Player(final String name, final boolean readiness) {
        this(new SimpleStringProperty(name), new SimpleBooleanProperty(readiness));
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public BooleanProperty readinessProperty() {
        return readinessProperty;
    }

    @Override
    public void receiveMessage(final ReceivedMessage message, final StringSender sender) {
        switch (message.value("event/action")) {
            case "user.readinessChanged" -> {
                this.readinessProperty.set(Boolean.parseBoolean(message.value("event/user/readiness")));
            }
            default -> {
                throw new RuntimeException("Unknown action: %s".formatted(message.value("event/action")));
            }
        }
    }
}
