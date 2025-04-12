package marksman.server.domain.transmittable;

import marksman.server.domain.Target;
import org.locationtech.jts.geom.Polygon;

import java.io.OutputStream;

public final class TransmittableTarget implements Target {
    private final Target origin;
    private final OutputStream outputStream;

    public TransmittableTarget(final Target origin, final OutputStream outputStream) {
        this.origin = origin;
        this.outputStream = outputStream;
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
        throw new RuntimeException("Not implemented yet");
    }
}
