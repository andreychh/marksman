package com.andreychh.marksman.server;

import com.andreychh.marksman.common.network.Message;
import com.andreychh.marksman.common.network.Session;
import com.andreychh.marksman.server.domain.Lobby;
import com.andreychh.marksman.server.domain.User;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

public final class Application {
    private final int port;
    private final Lobby lobby;

    public Application(final int port) {
        this.port = port;
        this.lobby = new Lobby();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                new Session(serverSocket.accept(), this::mux).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mux(final Message message, final OutputStream outputStream) {
        switch (message.value("action")) {
            case "lobby.join":
                this.lobby.add(new User(message.value("player.name"), outputStream));
                break;
            default:
                throw new RuntimeException(String.format("Unknown action: %s", message.value("action")));
        }
    }
}
