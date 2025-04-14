package marksman.server.domain.transmittable;

import marksman.server.domain.Target;
import org.locationtech.jts.geom.Polygon;

import java.io.OutputStream;

public final class TransmittableTarget implements Target {
    private final Target origin;
    private final OutputStream stream;

    public TransmittableTarget(final Target origin, final OutputStream stream) {
        this.origin = origin;
        this.stream = stream;
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
