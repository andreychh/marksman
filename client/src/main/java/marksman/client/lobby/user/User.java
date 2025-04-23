package marksman.client.lobby.user;

import javafx.beans.property.Property;
import marksman.shared.network.Message;

import java.io.IOException;
import java.io.OutputStream;

public final class User {
    private final OutputStream stream;
    private final Property<String> nameProperty;
    private final Property<Boolean> readinessProperty;

    public User(
            final OutputStream stream,
            final Property<String> nameProperty,
            final Property<Boolean> readinessProperty
    ) {
        this.stream = stream;
        this.nameProperty = nameProperty;
        this.readinessProperty = readinessProperty;
    }

    public void toggleReadiness() throws IOException {
        new Message()
                .with("action", "user.toggleReadiness")
                .with("user.name", this.nameProperty.getValue())
                .writeTo(this.stream);
    }

    public void leaveLobby() throws IOException {
        new Message()
                .with("action", "user.leaveLobby")
                .with("user.name", this.nameProperty.getValue())
                .writeTo(this.stream);
    }

    public Property<Boolean> readinessProperty() {
        return this.readinessProperty;
    }
}
