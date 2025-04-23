package marksman.client.app;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

import java.io.OutputStream;

public final class Users {
    private final OutputStream stream;
    private final StringProperty nameProperty;

    public Users(final OutputStream stream, final StringProperty nameProperty) {
        this.stream = stream;
        this.nameProperty = nameProperty;
    }

    public marksman.client.login.user.User loginUser() {
        return new marksman.client.login.user.User(this.stream, this.nameProperty);
    }

    public marksman.client.lobby.user.User lobbyUser() {
        marksman.client.lobby.user.User user = new marksman.client.lobby.user.User(
                this.stream,
                this.nameProperty,
                new SimpleBooleanProperty(false)
        );

        return user;
    }
}
