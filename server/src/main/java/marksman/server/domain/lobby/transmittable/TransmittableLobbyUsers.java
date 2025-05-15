package marksman.server.domain.lobby.transmittable;

import marksman.server.domain.lobby.LobbyUser;
import marksman.server.domain.lobby.LobbyUsers;
import marksman.shared.network.messaging.Message;
import marksman.shared.network.messaging.MessageSender;

public final class TransmittableLobbyUsers implements LobbyUsers {
    private final LobbyUsers origin;
    private final MessageSender sender;

    public TransmittableLobbyUsers(final LobbyUsers origin, final MessageSender sender) {
        this.origin = origin;
        this.sender = sender;
    }

    @Override
    public LobbyUser add(final String name, final boolean isReady) {
        TransmittableLobbyUser user = new TransmittableLobbyUser(this.origin.add(name, isReady), this.sender);
        this.sender.sendMessage(new Message()
                .with("action", "lobby.userAdded")
                .with("user.name", user.name())
                .with("user.readiness", String.valueOf(user.isReady())));
        return user;
    }

    @Override
    public void remove(final String username) {
        this.origin.remove(username);
        this.sender.sendMessage(new Message()
                .with("action", "lobby.userRemoved")
                .with("user.name", username));
    }

    @Override
    public LobbyUser get(final String username) {
        return new TransmittableLobbyUser(this.origin.get(username), this.sender);
    }

    @Override
    public boolean isEmpty() {
        return this.origin.isEmpty();
    }

    @Override
    public boolean isReady() {
        return this.origin.isReady();
    }

    @Override
    public String toString() {
        return this.origin.toString();
    }
}
