package marksman.client.game.user;

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

    public void fire() throws IOException {
        new Message()
                .with("action", "user.fire")
                .with("user.name", this.nameProperty.get())
                .writeTo(this.stream);
    }

    public void pauseGame() throws IOException {
        new Message()
                .with("action", "user.pauseGame")
                .with("user.name", this.nameProperty.get())
                .writeTo(this.stream);
    }
}
