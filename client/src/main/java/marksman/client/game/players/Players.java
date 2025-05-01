package marksman.client.game.players;

import javafx.collections.ObservableList;
import marksman.client.game.player.Player;
import marksman.shared.network.Message;
import marksman.shared.network.MessageHandler;

import java.io.OutputStream;

public final class Players implements MessageHandler {
    private final ObservableList<Player> players;

    public Players(final ObservableList<Player> players) {
        this.players = players;
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
    public void handleMessage(final Message message, final OutputStream stream) {
        switch (message.value("action")) {
            case "user.updateShoots", "user.updateHits" -> {
                this.get(message.value("user.name")).handleMessage(message, stream);
            }
            default -> {
                throw new RuntimeException("Unknown action: " + message.value("action"));
            }
        }
    }
}
