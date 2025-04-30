package marksman.server.domain;

import marksman.shared.network.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public final class User implements Sender {
    private final String name;
    private final OutputStream stream;
    private final List<Runnable> listeners;
    private boolean isReady;

    public User(final String name, final OutputStream stream) {
        this.name = name;
        this.stream = stream;
        this.listeners = new ArrayList<>();
        this.isReady = false;
    }

    public String name() {
        return name;
    }

    @Override
    public void send(final Message message) {
        try {
            message.writeTo(this.stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void toggleReadiness() {
        this.isReady = !this.isReady;
        this.listeners.forEach(Runnable::run);
    }

    public List<Runnable> listeners() {
        return this.listeners;
    }

    public boolean isReady() {
        return isReady;
    }
    @Override
    public String toString() {
        return String.format("%s~%s", name, isReady);
    }
}
