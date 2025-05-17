package marksman.server.domain.game.memory;

import marksman.server.domain.game.JTSPolygon;
import marksman.server.domain.game.Target;
import marksman.shared.geometry.Point;

import java.util.ArrayList;
import java.util.List;

public final class MemoryTarget implements Target {
    private final int id;
    private final DataSource dataSource;

    public MemoryTarget(final int id, final DataSource dataSource) {
        this.id = id;
        this.dataSource = dataSource;
    }

    @Override
    public void move() {
        this.dataSource.targetData().get(this.id).center = this.center().translate(this.direction());
    }

    @Override
    public void changeDirection() {
        this.dataSource.targetData().get(this.id).direction = this.direction().negate();
    }

    @Override
    public Point center() {
        return this.dataSource.targetData().get(this.id).center;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public JTSPolygon polygon() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double angle = 2 * Math.PI * i / 10;
            points.add(new Point(
                    this.center().x() + this.radius() * Math.cos(angle),
                    this.center().y() + this.radius() * Math.sin(angle)
            ));
        }
        return new JTSPolygon(points);
    }

    private double radius() {
        return this.dataSource.targetData().get(this.id).radius;
    }

    private Point direction() {
        return this.dataSource.targetData().get(this.id).direction;
    }
}
