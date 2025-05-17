package marksman.server.domain.game;

import marksman.shared.geometry.Point;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.util.List;
import java.util.stream.Stream;

public final class JTSPolygon {
    private final List<Point> points;

    public JTSPolygon(final List<Point> points) {
        this.points = points;
    }

    public boolean intersects(final JTSPolygon other) {
        return this.polygon().intersects(other.polygon());
    }

    public boolean contains(final JTSPolygon other) {
        return this.polygon().contains(other.polygon());
    }

    private Polygon polygon() {
        return new GeometryFactory().createPolygon(
                Stream.concat(this.points.stream(), Stream.of(this.points.getFirst()))
                        .map(p -> new Coordinate(p.x(), p.y()))
                        .toArray(Coordinate[]::new)
        );
    }

    public Element serialize() {
        Element element = DocumentHelper.createElement("points");
        this.points.forEach(p -> element.add(p.serialize()));
        return element;
    }
}
