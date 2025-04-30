package marksman.server;

import marksman.server.domain.Lobby;
import marksman.server.domain.User;
import marksman.shared.network.Message;
import marksman.shared.network.MessageDispatcher;

import java.io.IOException;

public final class Main {
    public static void main(final String[] args) {
        MessageDispatcher dispatcher = new MessageDispatcher();
        dispatcher.addHandler("app.connect", (message, stream) -> {
            try {
                new Message()
                        .with("action", "app.screenChanged")
                        .with("screen.name", "login")
                        .with("user.name", String.valueOf(stream.hashCode()))
                        .writeTo(stream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Lobby lobby = new Lobby();
        dispatcher.addHandler("user.joinLobby", (message, stream) -> {
            User user = new User(message.value("user.name"), stream);
            user.send(new Message()
                    .with("action", "app.screenChanged")
                    .with("screen.name", "lobby")
                    .with("user.name", String.valueOf(stream.hashCode()))
                    .with("user.readiness", String.valueOf(false))
                    .with("lobby.users", lobby.toString()));
            lobby.add(user);
        });

        dispatcher.addHandler("user.leaveLobby", (message, stream) -> {
            lobby.get(message.value("user.name")).send(new Message()
                    .with("action", "app.screenChanged")
                    .with("screen.name", "login")
                    .with("user.name", message.value("user.name")));
            lobby.remove(message.value("user.name"));
        });

        dispatcher.addHandler("user.toggleReadiness", (message, stream) -> {
            lobby.get(message.value("user.name")).toggleReadiness();
        });

        lobby.listeners().add(() -> {
            // environment = new Environment();
            // lobby.send(environment);
            // game = new Game(env);
            // game.start();
            lobby.send(new Message().with("action", "game.start"));
        });

        Application application = new Application(12345, dispatcher);
        application.start();
    }
}
