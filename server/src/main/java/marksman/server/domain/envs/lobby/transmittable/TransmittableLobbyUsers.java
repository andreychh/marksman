package marksman.server.domain.envs.lobby.transmittable;

import marksman.server.domain.envs.lobby.LobbyUser;
import marksman.server.domain.envs.lobby.LobbyUsers;
import marksman.shared.network.Message;
import marksman.shared.network.MessageSender;

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
        return this.origin.get(username);
    }

    @Override
    public boolean isEmpty() {
        return this.origin.isEmpty();
    }

    @Override
    public boolean isReady() {
        return this.origin.isReady();
    }
}
