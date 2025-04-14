package marksman.server;

import marksman.shared.network.Connection;
import marksman.shared.network.MessageDispatcher;

import java.io.IOException;
import java.net.ServerSocket;

// todo: rename
public final class Application {
    private final int port;
    private final MessageDispatcher dispatcher;

    public Application(final int port, final MessageDispatcher dispatcher) {
        this.port = port;
        this.dispatcher = dispatcher;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                new Connection(serverSocket.accept(), this.dispatcher).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
