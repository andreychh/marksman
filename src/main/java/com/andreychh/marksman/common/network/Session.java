package com.andreychh.marksman.common.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public final class Session implements AutoCloseable {
    private final Socket socket;
    private final MessageHandler handler;

    public Session(final Socket socket, final MessageHandler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    public void start() {
        new Thread(this::loop).start();
    }

    // todo: pool brakes SRP
    private void loop() {
        try (var in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                Message request = new Message();
                String[] pairs = line.split(";");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2) {
                        request.with(keyValue[0], keyValue[1]);
                    } else {
                        throw new IllegalArgumentException("Invalid message format");
                    }
                }
                handler.handle(request, this.outputStream());
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
