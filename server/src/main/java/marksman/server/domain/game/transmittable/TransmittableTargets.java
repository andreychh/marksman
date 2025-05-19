package marksman.server.domain.game.transmittable;

import marksman.server.domain.game.Target;
import marksman.server.domain.game.Targets;
import marksman.server.domain.game.events.GeometryAddedEvent;
import marksman.shared.network.connecting.StringSender;

import java.util.Iterator;

public final class TransmittableTargets implements Targets {
    private final Targets origin;
    private final StringSender sender;

    public TransmittableTargets(final Targets origin, final StringSender sender) {
        this.origin = origin;
        this.sender = sender;
    }

    @Override
    public Target add(final Target target) {
        Target added = new TransmittableTarget(this.origin.add(target), this.sender);
        new GeometryAddedEvent(added).sendTo(this.sender);
        return added;
    }

    @Override
    public Iterator<Target> iterator() {
        return this.origin.iterator();
    }
}
