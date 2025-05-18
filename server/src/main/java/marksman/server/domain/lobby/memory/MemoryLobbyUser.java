package marksman.server.domain.lobby.memory;

import marksman.server.domain.lobby.LobbyUser;
import org.dom4j.Element;

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
    public Element serialize() {
        Element userElement = org.dom4j.DocumentHelper.createElement("user");
        userElement.addElement("name").addText(this.name);
        userElement.addElement("readiness").addText(String.valueOf(this.isReady));
        return userElement;
    }
}
