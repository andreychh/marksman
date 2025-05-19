package marksman.server.domain.lobby.transmittable;

import marksman.server.domain.lobby.LobbyUser;
import marksman.server.domain.lobby.events.UserReadinessChangedEvent;
import marksman.shared.network.connecting.StringSender;
import org.dom4j.Element;

public final class TransmittableLobbyUser implements LobbyUser {
    private final LobbyUser origin;
    private final StringSender sender;

    public TransmittableLobbyUser(final LobbyUser origin, final StringSender sender) {
        this.origin = origin;
        this.sender = sender;
    }

    @Override
    public String name() {
        return this.origin.name();
    }

    @Override
    public boolean isReady() {
        return this.origin.isReady();
    }

    @Override
    public void toggleReadiness() {
        this.origin.toggleReadiness();
        new UserReadinessChangedEvent(this.origin).sendTo(this.sender);
    }

    @Override
    public Element serialize() {
        return this.origin.serialize();
    }
}
