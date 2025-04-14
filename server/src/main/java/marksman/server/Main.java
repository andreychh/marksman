package marksman.server;

import marksman.server.domain.Lobby;
import marksman.server.domain.User;
import marksman.shared.network.MapMessage;
import marksman.shared.network.MessageDispatcher;

import java.io.IOException;

public final class Main {
    public static void main(final String[] args) {
        MessageDispatcher dispatcher = new MessageDispatcher();

        Lobby lobby = new Lobby();
        dispatcher.addHandler("lobby.join", (message, stream) -> {
            try {
                lobby.add(new User(message.value("player.name"), stream));
                new MapMessage()
                        .with("action", "lobby.joined")
                        .writeTo(stream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Application application = new Application(12345, dispatcher);
        application.start();
    }
}
