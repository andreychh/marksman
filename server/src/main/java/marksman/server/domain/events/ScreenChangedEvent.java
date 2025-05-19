package marksman.server.domain.events;

import marksman.server.domain.Screen;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.Event;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class ScreenChangedEvent implements Event {
    private final Screen screen;

    public ScreenChangedEvent(final Screen screen) {
        this.screen = screen;
    }

    public Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("screen.changed");
        eventElement.add(this.screen.serialize());
        return eventElement;
    }

    public void sendTo(final StringSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
