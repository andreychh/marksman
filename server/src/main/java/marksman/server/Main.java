package marksman.server;

import marksman.server.domain.envs.lobby.LobbyUser;
import marksman.server.domain.envs.lobby.LobbyUsers;
import marksman.server.domain.envs.lobby.memory.MemoryLobbyUsers;
import marksman.server.domain.envs.lobby.transmittable.TransmittableLobbyUsers;
import marksman.shared.network.Connections;
import marksman.shared.network.Message;
import marksman.shared.network.MessageBus;

import java.util.ArrayList;

public final class Main {
    public static void main(final String[] args) {
        MessageBus messageBus = new MessageBus();
        Connections connections = new Connections(new ArrayList<>());
        LobbyUsers lobby = new TransmittableLobbyUsers(new MemoryLobbyUsers(new ArrayList<>()), connections);

        messageBus.addHandler("app.connect", (message, connection) -> {
            connection.sendMessage(new Message()
                    .with("action", "app.screenChanged")
                    .with("screen.name", "login")
                    .with("user.name", String.valueOf(connection.hashCode())));
        });

        messageBus.addHandler("user.joinLobby", (message, connection) -> {
            connections.add(connection);
            LobbyUser user = lobby.add(message.value("user.name"), false);

            connection.sendMessage(new Message()
                    .with("action", "app.screenChanged")
                    .with("screen.name", "lobby")
                    .with("user.name", user.name())
                    .with("user.readiness", String.valueOf(user.isReady()))
                    .with("lobby.users", lobby.toString()));
        });

        messageBus.addHandler("user.leaveLobby", (message, connection) -> {
            lobby.remove(message.value("user.name"));
            connections.remove(connection);

            connection.sendMessage(new Message()
                    .with("action", "app.screenChanged")
                    .with("screen.name", "login")
                    .with("user.name", message.value("user.name")));
        });

        messageBus.addHandler("user.toggleReadiness", (message, connection) -> {
            lobby.get(message.value("user.name")).toggleReadiness();

            if (!lobby.isReady()) {
                return;
            }

            // environment = new Environment();
            // lobby.send(environment);
            // game = new Game(env);
            // game.start();
            connections.sendMessage(new Message()
                    .with("action", "app.screenChanged")
                    .with("user.name", "USER")
                    .with("game.users", "null")
                    .with("screen.name", "game"));
        });

        Application application = new Application(12345, messageBus);
        application.start();
    }
}
