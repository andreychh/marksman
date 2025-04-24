package marksman.server.domain;

import marksman.shared.network.Message;

import java.util.ArrayList;
import java.util.List;

public final class Lobby implements Sender {
    private final List<User> users;

    public Lobby() {
        this.users = new ArrayList<>();
    }

    public void add(final User user) {
        this.users.add(user);
    }

    @Override
    public void send(final Message message) {
        this.users.forEach(u -> u.send(message));
    }
}
