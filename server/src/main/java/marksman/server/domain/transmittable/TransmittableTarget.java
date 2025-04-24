package marksman.server.domain.transmittable;

import marksman.server.domain.Sender;
import marksman.server.domain.Target;
import org.locationtech.jts.geom.Polygon;

public final class TransmittableTarget implements Target {
    private final Target origin;
    private final Sender sender;

    public TransmittableTarget(final Target origin, final Sender sender) {
        this.origin = origin;
        this.sender = sender;
    }

    @Override
    public void move() {
        this.origin.move();
        this.update();
    }

    @Override
    public void changeDirection() {
        this.origin.changeDirection();
    }

    @Override
    public Polygon polygon() {
        return this.origin.polygon();
    }

    private void update() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
