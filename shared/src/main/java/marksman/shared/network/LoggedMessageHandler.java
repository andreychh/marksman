package marksman.shared.network;

public final class LoggedMessageHandler implements MessageHandler {
    private final MessageHandler origin;

    public LoggedMessageHandler(final MessageHandler origin) {
        this.origin = origin;
    }

    @Override
    public void handleMessage(final Message message, final Connection connection) {
        System.out.println("Received message: " + message.content());
        this.origin.handleMessage(message, connection);
    }
}
