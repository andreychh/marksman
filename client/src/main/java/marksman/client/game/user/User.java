package marksman.client.game.user;

import javafx.beans.property.StringProperty;
import marksman.shared.network.Connection;
import marksman.shared.network.Message;

public final class User {
    private final Connection connection;
    private final StringProperty nameProperty;

    public User(final Connection connection, final StringProperty nameProperty) {
        this.connection = connection;
        this.nameProperty = nameProperty;
    }

    public void fire() {
        this.connection.sendMessage(new Message()
                .with("action", "user.fire")
                .with("user.name", this.nameProperty.get()));
    }

    public void pauseGame() {
        this.connection.sendMessage(new Message()
                .with("action", "user.pauseGame")
                .with("user.name", this.nameProperty.get()));
    }
}
