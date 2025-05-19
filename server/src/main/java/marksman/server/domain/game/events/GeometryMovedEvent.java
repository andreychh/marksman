package marksman.server.domain.game.events;

import marksman.server.domain.game.Geometry;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.Event;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class GeometryMovedEvent implements Event {
    private final Geometry geometry;

    public GeometryMovedEvent(final Geometry geometry) {
        this.geometry = geometry;
    }

    public Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("geometry.moved");
        Element geomElement = eventElement.addElement("geometry");
        geomElement.addElement("center").add(geometry.center().serialize());
        return eventElement;
    }

    public void sendTo(final StringSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
