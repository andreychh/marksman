package marksman.server.domain;

import marksman.shared.network.Message;

import java.io.IOException;
import java.io.OutputStream;

public final class User implements Sender {
    private final String name;
    private final OutputStream outputStream;

    public User(final String name, final OutputStream outputStream) {
        this.name = name;
        this.outputStream = outputStream;
    }

    public String name() {
        return name;
    }

    @Override
    public void send(final Message message) {
        try {
            message.writeTo(this.outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
