package marksman.server.domain.lobby;

import org.dom4j.Element;

public interface LobbyUser {
    String name();

    boolean isReady();

    void toggleReadiness();

    Element serialize();
}
