package marksman.server.domain.game.transmittable;

import marksman.server.domain.game.Polygon;
import marksman.server.domain.game.Target;
import marksman.server.domain.game.events.GeometryMovedEvent;
import marksman.shared.geometry.Point;
import marksman.shared.network.connecting.StringSender;

public final class TransmittableTarget implements Target {
    private final Target origin;
    private final StringSender sender;

    public TransmittableTarget(final Target origin, final StringSender sender) {
        this.origin = origin;
        this.sender = sender;
    }

    @Override
    public void move() {
        this.origin.move();
        new GeometryMovedEvent(this.origin).sendTo(this.sender);
    }

    @Override
    public void changeDirection() {
        this.origin.changeDirection();
    }

    @Override
    public Point center() {
        return this.origin.center();
    }

    @Override
    public int id() {
        return this.origin.id();
    }

    @Override
    public Target withId(final int id) {
        return new TransmittableTarget(this.origin.withId(id), this.sender);
    }

    @Override
    public Polygon polygon() {
        return this.origin.polygon();
    }
}
