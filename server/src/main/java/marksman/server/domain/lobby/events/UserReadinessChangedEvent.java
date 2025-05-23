package marksman.server.domain.lobby.events;

import marksman.server.domain.lobby.LobbyUser;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.Event;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class UserReadinessChangedEvent implements Event {
    private final LobbyUser user;

    public UserReadinessChangedEvent(final LobbyUser user) {
        this.user = user;
    }

    public Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("user.readinessChanged");
        eventElement.add(this.user.serialize());
        return eventElement;
    }

    @Override
    public void sendTo(final StringSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
