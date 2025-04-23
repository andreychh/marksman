package marksman.client.lobby.user;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import marksman.shared.network.Message;

import java.io.IOException;
import java.io.OutputStream;

public final class User {
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
}
