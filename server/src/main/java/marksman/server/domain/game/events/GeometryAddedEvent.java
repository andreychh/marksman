package marksman.server.domain.game.events;

import marksman.server.domain.game.Geometry;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.Event;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class GeometryAddedEvent implements Event {
    private final Geometry geometry;

    public GeometryAddedEvent(final Geometry geometry) {
        this.geometry = geometry;
    }

    public Element serialize() {
        Element eventElement = DocumentHelper.createElement("event");
        eventElement.addElement("action").addText("geometry.added");
        Element geomElement = eventElement.addElement("geometry");
        geomElement.addElement("id").addText(String.valueOf(geometry.id()));
        geomElement.add(this.geometry.polygon().serialize());
        return eventElement;
    }

    public void sendTo(final StringSender sender) {
        sender.sendString(this.serialize().asXML());
    }
}
