package com.andreychh.marksman.server.domain;

import org.locationtech.jts.geom.Polygon;

public interface Geometry {
    Polygon polygon();

    default boolean intersects(Geometry geometry) {
        return this.polygon().intersects(geometry.polygon());
    }

    default boolean contains(Geometry geometry) {
        return this.polygon().contains(geometry.polygon());
    }
}
