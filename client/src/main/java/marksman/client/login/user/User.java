package marksman.client.login.user;

import javafx.beans.property.StringProperty;
import marksman.shared.network.Message;

import java.io.IOException;
import java.io.OutputStream;

public final class User {
    private final OutputStream stream;
    private final StringProperty nameProperty;

    public User(final OutputStream stream, final StringProperty nameProperty) {
        this.stream = stream;
        this.nameProperty = nameProperty;
    }

    public void rename(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.nameProperty.set(name);
    }

    public void joinLobby() throws IOException {
        new Message()
                .with("action", "user.joinLobby")
                .with("user.name", this.nameProperty.get())
                .writeTo(this.stream);
    }
}
