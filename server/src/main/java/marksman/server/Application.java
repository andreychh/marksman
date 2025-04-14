package marksman.server;

import marksman.shared.network.MessageDispatcher;
import marksman.shared.network.Session;

import java.io.IOException;
import java.net.ServerSocket;

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
                new Session(serverSocket.accept(), this.dispatcher).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
