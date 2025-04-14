package marksman.shared.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public final class Connection implements AutoCloseable {
    private final Socket socket;
    private final MessageHandler handler;

    public Connection(final Socket socket, final MessageHandler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    public void start() {
        new Thread(this::loop).start();
    }

    private void loop() {
        try (var in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                handler.handleMessage(new InputAsMessage(line).message(), this.outputStream());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
    }

    public OutputStream outputStream() throws IOException {
        return this.socket.getOutputStream();
    }
}
