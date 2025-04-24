package marksman.server;

import marksman.server.domain.Lobby;
import marksman.server.domain.User;
import marksman.shared.network.Message;
import marksman.shared.network.MessageDispatcher;

public final class Main {
    public static void main(final String[] args) {
        MessageDispatcher dispatcher = new MessageDispatcher();

        Lobby lobby = new Lobby();
        dispatcher.addHandler("user.joinLobby", (message, stream) -> {
            User user = new User(message.value("user.name"), stream);
            lobby.add(user);
            user.send(new Message().with("action", "lobby.joined"));
        });

        Application application = new Application(12345, dispatcher);
        application.start();
    }
}
