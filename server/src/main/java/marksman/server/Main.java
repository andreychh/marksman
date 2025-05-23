package marksman.server;

import marksman.server.domain.events.ScreenChangedEvent;
import marksman.server.domain.game.Environment;
import marksman.server.domain.game.Game;
import marksman.server.domain.game.Identifiers;
import marksman.server.domain.game.Targets;
import marksman.server.domain.game.memory.MemoryField;
import marksman.server.domain.game.memory.MemoryFields;
import marksman.server.domain.game.memory.MemoryTarget;
import marksman.server.domain.game.memory.MemoryTargets;
import marksman.server.domain.game.transmittable.TransmittableTargets;
import marksman.server.domain.lobby.LobbyScreen;
import marksman.server.domain.lobby.LobbyUser;
import marksman.server.domain.lobby.LobbyUsers;
import marksman.server.domain.lobby.memory.MemoryLobbyUser;
import marksman.server.domain.lobby.memory.MemoryLobbyUsers;
import marksman.server.domain.lobby.transmittable.TransmittableLobbyUsers;
import marksman.server.domain.login.LoginScreen;
import marksman.server.domain.login.user.LoginUser;
import marksman.shared.geometry.Point;
import marksman.shared.geometry.Size;
import marksman.shared.network.connecting.Connections;
import marksman.shared.network.messaging.MessageBus;

public final class Main {
    public static void main(final String[] args) {
        MessageBus messageBus = new MessageBus();
        Connections connections = new Connections();
        LobbyUsers lobby = new TransmittableLobbyUsers(new MemoryLobbyUsers(), connections);

        messageBus.addHandler("app.connect", (message, sender) -> {
            new ScreenChangedEvent(new LoginScreen(new LoginUser(String.valueOf(sender.hashCode()))))
                    .sendTo(sender);
        });

        messageBus.addHandler("user.joinLobby", (message, sender) -> {
            connections.add(sender);
            LobbyUser user = lobby.add(new MemoryLobbyUser(message.value("event/user/name"), false));
            new ScreenChangedEvent(new LobbyScreen(user, lobby))
                    .sendTo(sender);
        });

        messageBus.addHandler("user.leaveLobby", (message, sender) -> {
            lobby.remove(message.value("event/user/name"));
            connections.remove(sender);
            new ScreenChangedEvent(new LoginScreen(new LoginUser(message.value("event/user/name"))))
                    .sendTo(sender);
        });

        messageBus.addHandler("user.toggleReadiness", (message, sender) -> {
            lobby.get(message.value("event/user/name")).toggleReadiness();

            if (!lobby.isReady()) {
                return;
            }

//            connections.sendMessage(new Message()
//                    .with("action", "app.screenChanged")
//                    .with("user.name", "USER")
//                    .with("game.users", "null")
//                    .with("screen.name", "game"));

            Identifiers identifiers = new Identifiers();
            Targets targets = new TransmittableTargets(new MemoryTargets(identifiers), connections);
            targets.add(new MemoryTarget(new Point(5, 5), 5.0, new Point(1, 1)));
            MemoryField field = new MemoryFields(identifiers).add(new MemoryField(new Point(0, 0), new Size(50, 50)));
            Environment environment = new Environment(targets, field);
            Game game = new Game(environment);
            game.start();
        });

        Application application = new Application(12345, messageBus);
        application.start();
    }
}
