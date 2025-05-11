package marksman.server.domain.envs.lobby.memory;

import marksman.server.domain.envs.lobby.LobbyUser;
import marksman.server.domain.envs.lobby.LobbyUsers;

import java.util.List;

public final class MemoryLobbyUsers implements LobbyUsers {
    private final List<LobbyUser> users;

    public MemoryLobbyUsers(final List<LobbyUser> users) {
        this.users = users;
    }


    @Override
    public LobbyUser add(final String name, final boolean isReady) {
        MemoryLobbyUser user = new MemoryLobbyUser(name, isReady);
        this.users.add(user);
        return user;
    }

    @Override
    public void remove(final String username) {
        this.users.removeIf(u -> u.name().equals(username));
    }

    @Override
    public LobbyUser get(final String name) {
        return this.users.stream()
                .filter(user -> user.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + name));
    }

    @Override
    public boolean isEmpty() {
        return this.users.isEmpty();
    }

    @Override
    public boolean isReady() {
        return this.users.stream().allMatch(LobbyUser::isReady);
    }
}
