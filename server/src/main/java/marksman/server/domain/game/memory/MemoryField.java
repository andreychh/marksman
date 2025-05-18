package marksman.server.domain.game.memory;

import marksman.server.domain.game.Geometry;
import marksman.server.domain.game.JTSPolygon;
import marksman.shared.geometry.Point;
import marksman.shared.geometry.Size;

import java.util.ArrayList;
import java.util.List;

public final class MemoryField implements Geometry {
    private final int id;
    private final Point center;
    private final Size size;

    private MemoryField(final int id, final Point center, final Size size) {
        this.id = id;
        this.center = center;
        this.size = size;
    }

    public MemoryField(final Point center, final Size size) {
        this(0, center, size);
    }

    public MemoryField withID(final int id) {
        return new MemoryField(id, this.center, this.size);
    }

    @Override
    public JTSPolygon polygon() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(this.size.width(), 0));
        points.add(new Point(this.size.width(), this.size.height()));
        points.add(new Point(0, this.size.height()));
        return new JTSPolygon(points);
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public Point center() {
        return this.center;
    }
}
