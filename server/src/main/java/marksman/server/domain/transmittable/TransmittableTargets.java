package marksman.server.domain.transmittable;

import marksman.server.domain.Targets;
import marksman.shared.geometry.Point;

import java.io.OutputStream;
import java.util.List;

public final class TransmittableTargets implements Targets {
    private final Targets origin;
    private final OutputStream stream;

    public TransmittableTargets(final Targets origin, final OutputStream stream) {
        this.origin = origin;
        this.stream = stream;
    }

    @Override
    public List<TransmittableTarget> targets() {
        return origin.targets()
                .stream()
                .map(t -> new TransmittableTarget(t, this.stream))
                .toList();
    }

    @Override
    public TransmittableTarget add(final Point center, final double radius, final Point direction) {
        return new TransmittableTarget(this.origin.add(center, radius, direction), this.stream);
    }
}
