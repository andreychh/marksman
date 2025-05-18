package marksman.server.domain.lobby;

import org.dom4j.Element;

public interface LobbyUsers {
    LobbyUser add(LobbyUser user);

    void remove(String name);

    LobbyUser get(String name);

    boolean isReady();

    Element serialize();
}
