package marksman.client.lobby.players;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import marksman.client.lobby.player.Player;
import marksman.shared.network.Connection;
import marksman.shared.network.Message;
import marksman.shared.network.MessageHandler;

public final class Players implements MessageHandler {
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
    public void handleMessage(final Message message, final Connection connection) {
        switch (message.value("action")) {
            case "lobby.userAdded" -> {
                this.add(message.value("user.name"), Boolean.parseBoolean(message.value("user.readiness")));
            }
            case "lobby.userRemoved" -> {
                this.remove(message.value("user.name"));
            }
            case "user.readinessChanged" -> {
                this.get(message.value("user.name")).handleMessage(message, connection);
            }
            default -> {
                throw new RuntimeException("Unknown action: " + message.value("action"));
            }
        }
    }
}
