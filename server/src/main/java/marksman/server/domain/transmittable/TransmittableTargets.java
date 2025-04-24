package marksman.server.domain.transmittable;

import marksman.server.domain.Sender;
import marksman.server.domain.Targets;
import marksman.shared.geometry.Point;

import java.util.List;

public final class TransmittableTargets implements Targets {
    private final Targets origin;
    private final Sender sender;

    public TransmittableTargets(final Targets origin, final Sender sender) {
        this.origin = origin;
        this.sender = sender;
    }

    @Override
    public List<TransmittableTarget> targets() {
        return origin.targets()
                .stream()
                .map(t -> new TransmittableTarget(t, this.sender))
                .toList();
    }

    @Override
    public TransmittableTarget add(final Point center, final double radius, final Point direction) {
        return new TransmittableTarget(this.origin.add(center, radius, direction), this.sender);
    }
}
