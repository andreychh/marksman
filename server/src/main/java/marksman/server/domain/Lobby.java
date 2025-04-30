package marksman.server.domain;

import marksman.shared.network.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Lobby implements Sender {
    private final Map<String, User> users;
    private final List<Runnable> listeners;

    public Lobby() {
        this.users = new HashMap<>();
        this.listeners = new ArrayList<>();
    }

    public void add(final User user) {
        user.listeners().add(this::onUserReady);
        this.users.put(user.name(), user);

        this.send(
                new Message()
                        .with("action", "lobby.userAdded")
                        .with("user.name", user.name())
                        .with("user.readiness", String.valueOf(user.isReady()))
        );
    }

    private void onUserReady() {
        this.users.values().forEach(u -> this.send(new Message()
                .with("action", "user.readinessChanged")
                .with("user.name", u.name())
                .with("user.readiness", String.valueOf(u.isReady()))));
        if (this.users.values().stream().allMatch(User::isReady)) {
            this.listeners.forEach(Runnable::run);
        }
    }

    public void remove(final String username) {
        this.users.get(username).listeners().remove((Runnable) this::onUserReady);
        this.users.remove(username);
        this.send(
                new Message()
                        .with("action", "lobby.userRemoved")
                        .with("user.name", username)
        );
    }

    @Override
    public void send(final Message message) {
        this.users.values().forEach(u -> u.send(message));
    }

    public List<Runnable> listeners() {
        return this.listeners;
    }

    public User get(final String name) {
        return this.users.get(name);
    }

    @Override
    public String toString() {
        if (this.users.isEmpty()) {
            return "null";
        }
        return this.users.values()
                .stream()
                .map(User::toString)
                .collect(Collectors.joining("%"));
    }
}
