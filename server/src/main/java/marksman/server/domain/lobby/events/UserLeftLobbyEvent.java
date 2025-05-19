package marksman.server.domain.lobby.events;

import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.Event;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class UserLeftLobbyEvent implements Event {
    private final String name;

    public UserLeftLobbyEvent(final String name) {
        this.name = name;
    }

    public Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("user.leftLobby");
        Element userElement = eventElement.addElement("user");
        userElement.addElement("name").addText(this.name);
        return eventElement;
    }

    @Override
    public void sendTo(final StringSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
