package marksman.shared.network.messaging;

import marksman.shared.network.connecting.Connection;

public final class LoggedMessageReceiver implements MessageReceiver {
    private final MessageReceiver origin;

    public LoggedMessageReceiver(final MessageReceiver origin) {
        this.origin = origin;
    }

    @Override
    public void receiveMessage(final ReceivedMessage message, final Connection connection) {
        System.out.println("Received message: " + message.value("action"));
        this.origin.receiveMessage(message, connection);
    }
}
