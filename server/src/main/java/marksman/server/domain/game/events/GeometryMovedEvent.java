package marksman.server.domain.game.events;

import marksman.server.domain.game.Geometry;
import marksman.shared.network.messaging.MessageSender;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class GeometryMovedEvent {
    private final Geometry geometry;

    public GeometryMovedEvent(final Geometry geometry) {
        this.geometry = geometry;
    }

    public Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("geometry.moved");
        Element geomElement = eventElement.addElement("geometry");
        geomElement.addElement("center").addText(String.valueOf(geometry.center()));
        return eventElement;
    }

    public void sendTo(final MessageSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
