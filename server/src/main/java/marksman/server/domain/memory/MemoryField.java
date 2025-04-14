package marksman.server.domain.memory;

import marksman.server.domain.Geometry;
import marksman.shared.geometry.Size;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

public final class MemoryField implements Geometry {
    private final int id;
    private final DataSource dataSource;

    public MemoryField(final int id, final DataSource dataSource) {
        this.id = id;
        this.dataSource = dataSource;
    }

    @Override
    public Polygon polygon() {
        Coordinate[] coordinates = new Coordinate[5];
        coordinates[0] = new Coordinate(0, 0);
        coordinates[1] = new Coordinate(this.size().width(), 0);
        coordinates[2] = new Coordinate(this.size().width(), this.size().height());
        coordinates[3] = new Coordinate(0, this.size().height());
        coordinates[4] = coordinates[0];
        return new GeometryFactory().createPolygon(coordinates);
    }

    public Size size() {
        return this.dataSource.fieldData().get(this.id).size;
    }
}
