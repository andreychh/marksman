package marksman.shared.network;

import java.io.OutputStream;

public final class LoggedMessageHandler implements MessageHandler {
    private final MessageHandler origin;

    public LoggedMessageHandler(final MessageHandler origin) {
        this.origin = origin;
    }

    @Override
    public void handleMessage(final Message message, final OutputStream stream) {
        System.out.println("Received message: " + message.content());
        this.origin.handleMessage(message, stream);
    }
}
