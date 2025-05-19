package marksman.client.lobby.user;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import marksman.client.lobby.events.UserLeaveLobbyEvent;
import marksman.client.lobby.events.UserToggleReadinessEvent;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.MessageReceiver;
import marksman.shared.network.messaging.ReceivedMessage;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class User implements MessageReceiver {
    private final StringSender sender;
    private final StringProperty nameProperty;
    private final BooleanProperty readinessProperty;

    public User(
            final StringSender sender,
            final StringProperty nameProperty,
            final BooleanProperty readinessProperty
    ) {
        this.sender = sender;
        this.nameProperty = nameProperty;
        this.readinessProperty = readinessProperty;
    }

    public User(final StringSender sender, final String name, final boolean readiness) {
        this(sender, new SimpleStringProperty(name), new SimpleBooleanProperty(readiness));
    }

    public void toggleReadiness() {
        new UserToggleReadinessEvent(this).sendTo(this.sender);
    }

    public void leaveLobby() {
        new UserLeaveLobbyEvent(this).sendTo(this.sender);
    }

    public BooleanProperty readinessProperty() {
        return this.readinessProperty;
    }

    @Override
    public void receiveMessage(final ReceivedMessage message, final StringSender sender) {
        switch (message.value("event/action")) {
            case "user.readinessChanged" -> {
                if (!message.value("event/user/name").equals(this.nameProperty.get())) {
                    break;
                }
                boolean readiness = Boolean.parseBoolean(message.value("event/user/readiness"));
                Platform.runLater(() -> this.readinessProperty.set(readiness));
            }
            default -> {
                throw new RuntimeException("Unknown action: %s".formatted(message.value("event/action")));
            }
        }
    }

    public Element serialize() {
        Element userElement = DocumentHelper.createElement("user");
        userElement.addElement("name").addText(this.nameProperty.get());
        return userElement;
    }
}
