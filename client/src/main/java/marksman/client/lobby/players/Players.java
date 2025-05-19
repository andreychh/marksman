package marksman.client.lobby.players;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import marksman.client.lobby.player.Player;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.MessageReceiver;
import marksman.shared.network.messaging.ReceivedMessage;

public final class Players implements MessageReceiver {
    private final ObservableList<Player> players;

    public Players(final ObservableList<Player> players) {
        this.players = players;
    }

    private void add(final String name, final boolean readiness) {
        this.players.add(new Player(
                new SimpleStringProperty(name),
                new SimpleBooleanProperty(readiness)
        ));
    }

    public void remove(final String name) {
        this.players.removeIf(player -> player.nameProperty().get().equals(name));
    }

    public Player get(final String name) {
        return this.players.stream()
                .filter(player -> player.nameProperty().get().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Player not found: " + name));
    }

    public ObservableList<Player> listProperty() {
        return this.players;
    }

    @Override
    public void receiveMessage(final ReceivedMessage message, final StringSender sender) {
        switch (message.value("event/action")) {
            case "user.joinedLobby" -> {
                this.add(message.value("event/user/name"), Boolean.parseBoolean(message.value("event/user/readiness")));
            }
            case "user.leftLobby" -> {
                this.remove(message.value("event/user/name"));
            }
            case "user.readinessChanged" -> {
                this.get(message.value("event/user/name")).receiveMessage(message, sender);
            }
            default -> {
                throw new RuntimeException("Unknown action: %s".formatted(message.value("event/action")));
            }
        }
    }
}
