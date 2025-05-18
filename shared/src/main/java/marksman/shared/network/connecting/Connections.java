package marksman.shared.network.connecting;

import marksman.shared.network.messaging.MessageSender;
import marksman.shared.network.messaging.SendableMessage;

import java.util.List;

public final class Connections implements MessageSender {
    private final List<Connection> connections;

    public Connections(final List<Connection> connections) {
        this.connections = connections;
    }

    public void add(final Connection connection) {
        this.connections.add(connection);
    }

    public void remove(final Connection connection) {
        this.connections.remove(connection);
    }

    @Override
    public void sendMessage(final SendableMessage message) {
        this.connections.forEach(c -> c.sendMessage(message));
    }

    @Override
    public void sendString(final String string) {
        this.connections.forEach(c -> c.sendString(string));
    }
}
