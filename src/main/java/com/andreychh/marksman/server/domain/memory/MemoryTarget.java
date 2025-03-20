package com.andreychh.marksman.server.domain.memory;

import com.andreychh.marksman.server.Point;
import com.andreychh.marksman.server.domain.Target;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

public class MemoryTarget implements Target {
    private final int id;
    private final DataSource dataSource;

    public MemoryTarget(int id, DataSource dataSource) {
        this.id = id;
        this.dataSource = dataSource;
    }

    @Override
    public void move() {
        this.dataSource.targetData().get(this.id).center = this.center().translate(this.direction());
    }

    @Override
    public void onFieldEscape() {
        this.dataSource.targetData().get(this.id).direction = this.direction().negate();
    }

    @Override
    public Polygon polygon() {
        Coordinate[] coordinates = new Coordinate[10 + 1];
        for (int i = 0; i < 10; i++) {
            double angle = 2 * Math.PI * i / 10;
            double x = this.center().x() + this.radius() * Math.cos(angle);
            double y = this.center().y() + this.radius() * Math.sin(angle);
            coordinates[i] = new Coordinate(x, y);
        }
        coordinates[10] = coordinates[0];
        return new GeometryFactory().createPolygon(coordinates);
    }

    private Point center() {
        return this.dataSource.targetData().get(this.id).center;
    }

    private double radius() {
        return this.dataSource.targetData().get(this.id).radius;
    }

    private Point direction() {
        return this.dataSource.targetData().get(this.id).direction;
    }
}
