package marksman.server.domain.lobby;

import marksman.server.domain.Screen;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class LobbyScreen implements Screen {
    private final LobbyUser user;
    private final LobbyUsers users;

    public LobbyScreen(final LobbyUser user, final LobbyUsers users) {
        this.user = user;
        this.users = users;
    }

    public Element serialize() {
        Element element = DocumentHelper.createElement("screen");
        element.addElement("name").addText("lobby");
        element.add(this.user.serialize());
        element.add(this.users.serialize());
        return element;
    }
}
