package marksman.client.game.player;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.MessageReceiver;
import marksman.shared.network.messaging.ReceivedMessage;

public final class Player implements MessageReceiver {
    private final StringProperty nameProperty;
    private final IntegerProperty shootsProperty;
    private final IntegerProperty hitsProperty;

    public Player(
            final StringProperty nameProperty,
            final IntegerProperty shootsProperty,
            final IntegerProperty hitsProperty
    ) {
        this.nameProperty = nameProperty;
        this.shootsProperty = shootsProperty;
        this.hitsProperty = hitsProperty;
    }

    public Player(final String name, final int shoots, final int hits) {
        this(new SimpleStringProperty(name), new SimpleIntegerProperty(shoots), new SimpleIntegerProperty(hits));
    }

    public StringProperty nameProperty() {
        return this.nameProperty;
    }

    public IntegerProperty shootsProperty() {
        return this.shootsProperty;
    }

    public IntegerProperty hitsProperty() {
        return this.hitsProperty;
    }

    @Override
    public void receiveMessage(final ReceivedMessage message, final StringSender sender) {
        switch (message.value("event/action")) {
            case "user.updateShoots" -> {
                this.shootsProperty.set(Integer.parseInt(message.value("event/user/shoots")));
            }
            case "user.updateHits" -> {
                this.hitsProperty.set(Integer.parseInt(message.value("event/user/hits")));
            }
            default -> {
                throw new RuntimeException("Unknown action: %s".formatted(message.value("event/action")));
            }
        }
    }
}
