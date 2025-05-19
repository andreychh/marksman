package marksman.server.domain.lobby.transmittable;

import marksman.server.domain.lobby.LobbyUser;
import marksman.server.domain.lobby.LobbyUsers;
import marksman.server.domain.lobby.events.UserJoinedLobbyEvent;
import marksman.server.domain.lobby.events.UserLeftLobbyEvent;
import marksman.shared.network.connecting.StringSender;
import org.dom4j.Element;

public final class TransmittableLobbyUsers implements LobbyUsers {
    private final LobbyUsers origin;
    private final StringSender sender;

    public TransmittableLobbyUsers(final LobbyUsers origin, final StringSender sender) {
        this.origin = origin;
        this.sender = sender;
    }

    @Override
    public LobbyUser add(final LobbyUser user) {
        LobbyUser added = new TransmittableLobbyUser(this.origin.add(user), this.sender);
        new UserJoinedLobbyEvent(added).sendTo(this.sender);
        return added;
    }

    @Override
    public void remove(final String name) {
        this.origin.remove(name);
        new UserLeftLobbyEvent(name).sendTo(this.sender);
    }

    @Override
    public LobbyUser get(final String username) {
        return new TransmittableLobbyUser(this.origin.get(username), this.sender);
    }

    @Override
    public boolean isReady() {
        return this.origin.isReady();
    }

    @Override
    public Element serialize() {
        return this.origin.serialize();
    }
}
