package marksman.client.events;

import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.Event;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class ConnectEvent implements Event {
    private Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("app.connect");
        return eventElement;
    }

    @Override
    public void sendTo(final StringSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
