package marksman.client.login;

import marksman.client.DataSource;
import marksman.shared.network.Message;

import java.io.IOException;
import java.io.OutputStream;

public final class User {
    private final OutputStream stream;
    private final DataSource dataSource;

    public User(final OutputStream stream, final DataSource dataSource) {
        this.stream = stream;
        this.dataSource = dataSource;
    }

    public void rename(final String name) {
        // todo: implement validation as decorator
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.dataSource.userName = name;
    }

    public void joinLobby() throws IOException {
        new Message()
                .with("action", "user.joinLobby")
                .with("user.name", this.dataSource.userName)
                .writeTo(this.stream);
    }
}
