package marksman.server.domain.game;

public interface Geometry {
    JTSPolygon polygon();

    default boolean intersects(Geometry geometry) {
        return this.polygon().intersects(geometry.polygon());
    }

    default boolean contains(Geometry geometry) {
        return this.polygon().contains(geometry.polygon());
    }

    int id();
}
