package marksman.client.login;

import javafx.beans.property.Property;
import marksman.shared.network.Message;

import java.io.IOException;
import java.io.OutputStream;

public final class User {
    private final OutputStream stream;
    private final Property<String> nameProperty;

    public User(final OutputStream stream, final Property<String> nameProperty) {
        this.stream = stream;
        this.nameProperty = nameProperty;
    }

    public void rename(final String name) {
        // todo: implement validation as decorator
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.nameProperty.setValue(name);
    }

    public void joinLobby() throws IOException {
        new Message()
                .with("action", "user.joinLobby")
                .with("user.name", this.nameProperty.getValue())
                .writeTo(this.stream);
    }
}
