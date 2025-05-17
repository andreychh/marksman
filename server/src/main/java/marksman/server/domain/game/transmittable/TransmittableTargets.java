package marksman.server.domain.game.transmittable;

import marksman.server.domain.game.Target;
import marksman.server.domain.game.Targets;
import marksman.shared.network.messaging.Message;
import marksman.shared.network.messaging.MessageSender;

import java.util.Iterator;

public final class TransmittableTargets implements Targets {
    private final Targets origin;
    private final MessageSender sender;

    public TransmittableTargets(final Targets origin, final MessageSender sender) {
        this.origin = origin;
        this.sender = sender;
    }

    @Override
    public Target add(final Target target) {
        Target added = new TransmittableTarget(this.origin.add(target), this.sender);
        this.sender.sendMessage(new Message()
                .with("action", "geometry.added")
                .with("geometry.center", added.center().toString())
                .with("geometry.points", added.toString()));
        return added;
    }

    @Override
    public Iterator<Target> iterator() {
        return this.origin.iterator();
    }
}
