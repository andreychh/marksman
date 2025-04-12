package marksman.server.domain;

import marksman.server.MultiOutputStream;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public final class Lobby {
    private final List<User> users;

    public Lobby() {
        this.users = new ArrayList<>();
    }

    public void add(final User user) {
        this.users.add(user);
    }

    public OutputStream outputStream() {
        return new MultiOutputStream(this.users.stream()
                .map(User::outputStream)
                .toArray(OutputStream[]::new));
    }
}
