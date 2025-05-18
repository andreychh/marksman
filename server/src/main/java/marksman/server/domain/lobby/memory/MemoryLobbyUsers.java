package marksman.server.domain.lobby.memory;

import marksman.server.domain.lobby.LobbyUser;
import marksman.server.domain.lobby.LobbyUsers;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public final class MemoryLobbyUsers implements LobbyUsers {
    private final List<LobbyUser> users;

    public MemoryLobbyUsers(final List<LobbyUser> users) {
        this.users = users;
    }

    public MemoryLobbyUsers() {
        this(new ArrayList<>());
    }

    @Override
    public LobbyUser add(final LobbyUser user) {
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
                .filter(u -> u.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + name));
    }

    @Override
    public boolean isReady() {
        return this.users.stream().allMatch(LobbyUser::isReady);
    }

    @Override
    public Element serialize() {
        Element element = DocumentHelper.createElement("users");
        this.users.forEach(u -> element.add(u.serialize()));
        return element;
    }
}
