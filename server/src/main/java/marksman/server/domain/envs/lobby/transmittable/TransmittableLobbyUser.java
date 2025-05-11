package marksman.server.domain.envs.lobby.transmittable;

import marksman.server.domain.envs.lobby.LobbyUser;
import marksman.shared.network.Message;
import marksman.shared.network.MessageSender;

public final class TransmittableLobbyUser implements LobbyUser {
    private final LobbyUser origin;
    private final MessageSender sender;

    public TransmittableLobbyUser(final LobbyUser origin, final MessageSender sender) {
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
        this.sender.sendMessage(new Message()
                .with("action", "user.toggleReadiness")
                .with("user.name", this.origin.name())
                .with("user.readiness", String.valueOf(this.origin.isReady()))
        );
    }
}
