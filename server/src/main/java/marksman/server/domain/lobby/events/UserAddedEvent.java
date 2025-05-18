package marksman.server.domain.lobby.events;

import marksman.server.domain.lobby.LobbyUser;
import marksman.shared.network.messaging.MessageSender;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class UserAddedEvent {
    private final LobbyUser user;

    public UserAddedEvent(final LobbyUser user) {
        this.user = user;
    }

    public Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("user.added");
        eventElement.add(this.user.serialize());
        return eventElement;
    }

    public void sendTo(final MessageSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
