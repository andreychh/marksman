package marksman.shared.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public final class Connection implements AutoCloseable, MessageSender {
    private final Socket socket;
    private final MessageHandler handler;

    public Connection(final Socket socket, final MessageHandler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    public void start() {
        new Thread(this::loop).start();
    }

    @Override
    public void sendMessage(final Message message) {
        try {
            this.socket.getOutputStream().write(message.content().getBytes());
            this.socket.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: Handle exception
        }
    }

    @Override
    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: Handle exception
        }
    }

    private void loop() {
        try (var in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                handler.handleMessage(new InputAsMessage(line).message(), this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: Handle exception
        }
    }
}
