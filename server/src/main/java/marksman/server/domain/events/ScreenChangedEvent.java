package marksman.server.domain.events;

import marksman.server.domain.Screen;
import marksman.shared.network.messaging.MessageSender;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class ScreenChangedEvent {
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

    public void sendTo(final MessageSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
