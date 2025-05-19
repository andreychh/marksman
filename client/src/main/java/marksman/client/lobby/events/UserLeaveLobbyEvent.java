package marksman.client.lobby.events;

import marksman.client.lobby.user.User;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.Event;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class UserLeaveLobbyEvent implements Event {
    private final User user;

    public UserLeaveLobbyEvent(final User user) {
        this.user = user;
    }

    private Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("user.leaveLobby");
        eventElement.add(this.user.serialize());
        return eventElement;
    }

    @Override
    public void sendTo(final StringSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
