package marksman.server.domain.game.memory;

import marksman.server.domain.game.Geometry;
import marksman.server.domain.game.JTSPolygon;
import marksman.shared.geometry.Point;
import marksman.shared.geometry.Size;

import java.util.ArrayList;
import java.util.List;

public final class MemoryField implements Geometry {
    private final int id;
    private final DataSource dataSource;

    public MemoryField(final int id, final DataSource dataSource) {
        this.id = id;
        this.dataSource = dataSource;
    }

    @Override
    public JTSPolygon polygon() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(this.size().width(), 0));
        points.add(new Point(this.size().width(), this.size().height()));
        points.add(new Point(0, this.size().height()));
        return new JTSPolygon(points);
    }

    private Size size() {
        return this.dataSource.fieldData().get(this.id).size;
    }
}
