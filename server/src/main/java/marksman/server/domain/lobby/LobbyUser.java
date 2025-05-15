package marksman.server.domain.lobby;

public interface LobbyUser {
    String name();

    boolean isReady();

    void toggleReadiness();
}
