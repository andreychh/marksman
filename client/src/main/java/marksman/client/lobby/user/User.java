package marksman.client.lobby.user;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import marksman.shared.network.connecting.Connection;
import marksman.shared.network.messaging.Message;
import marksman.shared.network.messaging.MessageReceiver;
import marksman.shared.network.messaging.ReceivedMessage;

public final class User implements MessageReceiver {
    private final Connection connection;
    private final StringProperty nameProperty;
    private final BooleanProperty readinessProperty;

    public User(
            final Connection connection,
            final StringProperty nameProperty,
            final BooleanProperty readinessProperty
    ) {
        this.connection = connection;
        this.nameProperty = nameProperty;
        this.readinessProperty = readinessProperty;
    }

    public void toggleReadiness() {
        this.connection.sendMessage(new Message()
                .with("action", "user.toggleReadiness")
                .with("user.name", this.nameProperty.get()));
    }

    public void leaveLobby() {
        this.connection.sendMessage(new Message()
                .with("action", "user.leaveLobby")
                .with("user.name", this.nameProperty.get()));
    }

    public BooleanProperty readinessProperty() {
        return this.readinessProperty;
    }

    @Override
    public void receiveMessage(final ReceivedMessage message, final Connection connection) {
        switch (message.value("action")) {
            case "user.readinessChanged" -> {
                if (!message.value("user.name").equals(this.nameProperty.get())) {
                    break;
                }
                boolean readiness = Boolean.parseBoolean(message.value("user.readiness"));
                Platform.runLater(() -> this.readinessProperty.set(readiness));
            }
            default -> {
                throw new RuntimeException("Unknown action: " + message.value("action"));
            }
        }
    }
}
