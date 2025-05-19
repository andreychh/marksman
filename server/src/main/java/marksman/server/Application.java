package marksman.server;

import marksman.shared.network.connecting.Connection;
import marksman.shared.network.messaging.MessageBus;

import java.io.IOException;
import java.net.ServerSocket;

// todo: rename
public final class Application {
    private final int port;
    private final MessageBus messageBus;

    public Application(final int port, final MessageBus messageBus) {
        this.port = port;
        this.messageBus = messageBus;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                new Connection(serverSocket.accept(), this.messageBus).listen();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
