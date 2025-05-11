package marksman.server.domain.envs.lobby;

public interface LobbyUser {
    String name();

    boolean isReady();

    void toggleReadiness();
}
