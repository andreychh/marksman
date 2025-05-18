package marksman.server.domain.lobby.events;

import marksman.shared.network.messaging.MessageSender;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class UserRemovedEvent {
    private final String name;

    public UserRemovedEvent(final String name) {
        this.name = name;
    }

    public Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("user.removed");
        Element userElement = eventElement.addElement("user");
        userElement.addElement("name").addText(this.name);
        return eventElement;
    }

    public void sendTo(final MessageSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
