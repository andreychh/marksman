package marksman.server;

import marksman.server.domain.game.Environment;
import marksman.server.domain.game.Game;
import marksman.server.domain.game.Identifiers;
import marksman.server.domain.game.Targets;
import marksman.server.domain.game.memory.MemoryField;
import marksman.server.domain.game.memory.MemoryFields;
import marksman.server.domain.game.memory.MemoryTarget;
import marksman.server.domain.game.memory.MemoryTargets;
import marksman.server.domain.game.transmittable.TransmittableTarget;
import marksman.server.domain.game.transmittable.TransmittableTargets;
import marksman.server.domain.lobby.LobbyUser;
import marksman.server.domain.lobby.LobbyUsers;
import marksman.server.domain.lobby.memory.MemoryLobbyUsers;
import marksman.server.domain.lobby.transmittable.TransmittableLobbyUsers;
import marksman.shared.geometry.Point;
import marksman.shared.geometry.Size;
import marksman.shared.network.connecting.Connections;
import marksman.shared.network.messaging.Message;
import marksman.shared.network.messaging.MessageBus;

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

            connections.sendMessage(new Message()
                    .with("action", "app.screenChanged")
                    .with("user.name", "USER")
                    .with("game.users", "null")
                    .with("screen.name", "game"));

            Identifiers identifiers = new Identifiers();
            Targets targets = new TransmittableTargets(new MemoryTargets(identifiers), connections);
            targets.add(new TransmittableTarget(new MemoryTarget(new Point(5, 5), 5.0, new Point(1, 1)), connections));
            MemoryField field = new MemoryFields(identifiers).add(new MemoryField(new Point(0, 0), new Size(50, 50)));
            Environment environment = new Environment(targets, field);
            Game game = new Game(environment);
            game.start();
        });

        Application application = new Application(12345, messageBus);
        application.start();
    }
}
