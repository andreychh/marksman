package marksman.server.domain.game.transmittable;

import marksman.server.domain.game.JTSPolygon;
import marksman.server.domain.game.Target;
import marksman.shared.geometry.Point;
import marksman.shared.network.messaging.Message;
import marksman.shared.network.messaging.MessageSender;

public final class TransmittableTarget implements Target {
    private final Target origin;
    private final MessageSender sender;

    public TransmittableTarget(final Target origin, final MessageSender sender) {
        this.origin = origin;
        this.sender = sender;
    }

    @Override
    public void move() {
        this.origin.move();
        this.sender.sendMessage(new Message()
                .with("action", "geometry.moved")
                .with("geometry.id", String.valueOf(this.id()))
                .with("geometry.center", this.center().toString()));
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
    public JTSPolygon polygon() {
        return this.origin.polygon();
    }
}
