package marksman.client.login.events;

import marksman.client.login.user.User;
import marksman.shared.network.connecting.StringSender;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class UserJoinLobbyEvent {
    private final User user;

    public UserJoinLobbyEvent(final User user) {
        this.user = user;
    }

    private Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("user.joinLobby");
        eventElement.add(this.user.serialize());
        return eventElement;
    }

    public void sendTo(final StringSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
