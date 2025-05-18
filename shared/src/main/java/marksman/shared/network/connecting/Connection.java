package marksman.shared.network.connecting;

import marksman.shared.network.messaging.InputAsMessage;
import marksman.shared.network.messaging.MessageReceiver;
import marksman.shared.network.messaging.MessageSender;
import marksman.shared.network.messaging.SendableMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public final class Connection implements AutoCloseable, MessageSender {
    private final Socket socket;
    private final MessageReceiver receiver;

    public Connection(final Socket socket, final MessageReceiver receiver) {
        this.socket = socket;
        this.receiver = receiver;
    }

    public void start() {
        new Thread(this::loop).start();
    }

    @Override
    public void sendMessage(final SendableMessage message) {
        try {
            this.socket.getOutputStream().write(message.content().getBytes());
            this.socket.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: Handle exception
        }
    }

    @Override
    public void sendString(final String string) {
        try {
            this.socket.getOutputStream().write(string.getBytes());
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
                receiver.receiveMessage(new InputAsMessage(line).message(), this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: Handle exception
        }
    }
}
