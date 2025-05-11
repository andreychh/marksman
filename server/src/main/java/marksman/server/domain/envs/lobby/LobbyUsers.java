package marksman.server.domain.envs.lobby;

public interface LobbyUsers {
    LobbyUser add(String name, boolean isReady);

    void remove(String name);

    LobbyUser get(String name);

    boolean isEmpty();

    boolean isReady();
}
