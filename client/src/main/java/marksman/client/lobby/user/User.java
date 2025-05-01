package marksman.client.lobby.user;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import marksman.shared.network.Message;
import marksman.shared.network.MessageHandler;

import java.io.IOException;
import java.io.OutputStream;

public final class User implements MessageHandler {
    private final OutputStream stream;
    private final StringProperty nameProperty;
    private final BooleanProperty readinessProperty;

    public User(
            final OutputStream stream,
            final StringProperty nameProperty,
            final BooleanProperty readinessProperty
    ) {
        this.stream = stream;
        this.nameProperty = nameProperty;
        this.readinessProperty = readinessProperty;
    }

    public void toggleReadiness() throws IOException {
        new Message()
                .with("action", "user.toggleReadiness")
                .with("user.name", this.nameProperty.get())
                .writeTo(this.stream);
    }

    public void leaveLobby() throws IOException {
        new Message()
                .with("action", "user.leaveLobby")
                .with("user.name", this.nameProperty.get())
                .writeTo(this.stream);
    }

    public BooleanProperty readinessProperty() {
        return this.readinessProperty;
    }

    @Override
    public void handleMessage(final Message message, final OutputStream stream) {
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
