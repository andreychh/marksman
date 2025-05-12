package marksman.server.domain.envs.lobby.memory;

import marksman.server.domain.envs.lobby.LobbyUser;

public final class MemoryLobbyUser implements LobbyUser {
    private final String name;
    private boolean isReady;

    public MemoryLobbyUser(final String name, final boolean isReady) {
        this.name = name;
        this.isReady = isReady;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void toggleReadiness() {
        this.isReady = !this.isReady;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    @Override
    public String toString() {
        return String.format("%s~%s", name, isReady);
    }
}
