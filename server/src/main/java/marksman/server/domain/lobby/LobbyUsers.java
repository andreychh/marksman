package marksman.server.domain.lobby;

public interface LobbyUsers {
    LobbyUser add(String name, boolean isReady);

    void remove(String name);

    LobbyUser get(String name);

    boolean isEmpty();

    boolean isReady();
}
