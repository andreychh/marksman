package marksman.client.game.player;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import marksman.shared.network.Connection;
import marksman.shared.network.Message;
import marksman.shared.network.MessageHandler;

public final class Player implements MessageHandler {
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
    public void handleMessage(final Message message, final Connection connection) {
        switch (message.value("action")) {
            case "user.updateShoots" -> {
                this.shootsProperty.set(Integer.parseInt(message.value("user.shoots")));
            }
            case "user.updateHits" -> {
                this.hitsProperty.set(Integer.parseInt(message.value("user.hits")));
            }
            default -> {
                throw new RuntimeException("Unknown action: " + message.value("action"));
            }
        }
    }
}
