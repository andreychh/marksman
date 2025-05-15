package marksman.server.domain.game.transmittable;

import marksman.server.domain.game.Target;
import marksman.server.domain.game.Targets;
import marksman.shared.geometry.Point;
import marksman.shared.network.messaging.Message;
import marksman.shared.network.messaging.MessageSender;

import java.util.List;

public final class TransmittableTargets implements Targets {
    private final Targets origin;
    private final MessageSender sender;

    public TransmittableTargets(final Targets origin, final MessageSender sender) {
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
        Target target = this.origin.add(center, radius, direction);

        this.sender.sendMessage(new Message()
                .with("action", "geometry.added")
                .with("geometry.center", target.center().toString())
                .with("geometry.points", target.toString()));

        return new TransmittableTarget(target, this.sender);
    }
}
