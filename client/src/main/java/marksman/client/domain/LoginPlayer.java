package marksman.client.domain;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import marksman.shared.network.Message;

import java.io.IOException;
import java.io.OutputStream;

public final class LoginPlayer {
    private final OutputStream stream;
    private final SimpleStringProperty nameProperty;

    public LoginPlayer(final OutputStream stream) {
        this.stream = stream;
        this.nameProperty = new SimpleStringProperty();
    }

    public Property<String> nameProperty() {
        return this.nameProperty;
    }

    public void joinLobby() throws IOException {
        new Message()
                .with("action", "lobby.join")
                .with("player.name", this.nameProperty.get())
                .writeTo(this.stream);
    }
}
