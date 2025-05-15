package marksman.client.login.user;

import javafx.beans.property.SimpleStringProperty;
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

    public User(final Connection connection, final String name) {
        this(connection, new SimpleStringProperty(name));
    }

    public void rename(final String name) {
        // todo: move validation to server side
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.nameProperty.set(name);
    }

    public void joinLobby() {
        this.connection.sendMessage(new Message()
                .with("action", "user.joinLobby")
                .with("user.name", this.nameProperty.get())
        );
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }
}
