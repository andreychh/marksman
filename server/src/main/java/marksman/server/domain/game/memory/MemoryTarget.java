package marksman.server.domain.game.memory;

import marksman.server.domain.game.Polygon;
import marksman.server.domain.game.Target;
import marksman.shared.geometry.Point;

import java.util.ArrayList;
import java.util.List;

public final class MemoryTarget implements Target {
    private final int id;
    private Point center;
    private final double radius;
    private Point direction;

    private MemoryTarget(final int id, final Point center, final double radius, final Point direction) {
        this.id = id;
        this.center = center;
        this.radius = radius;
        this.direction = direction;
    }

    public MemoryTarget(final Point center, final double radius, final Point direction) {
        this(0, center, radius, direction);
    }

    @Override
    public MemoryTarget withId(final int id) {
        return new MemoryTarget(id, this.center, this.radius, this.direction);
    }

    @Override
    public void move() {
        this.center = this.center.translate(this.direction);
    }

    @Override
    public void changeDirection() {
        this.direction = this.direction.negate();
    }

    @Override
    public Point center() {
        return this.center;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public Polygon polygon() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double angle = 2 * Math.PI * i / 10;
            // todo: encapsulate logic in Point class
            points.add(new Point(
                    this.center.x() + this.radius * Math.cos(angle),
                    this.center.y() + this.radius * Math.sin(angle)
            ));
        }
        return new Polygon(points);
    }
}
