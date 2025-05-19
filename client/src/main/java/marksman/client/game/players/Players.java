package marksman.client.game.players;

import javafx.collections.ObservableList;
import marksman.client.game.player.Player;
import marksman.shared.network.connecting.StringSender;
import marksman.shared.network.messaging.MessageReceiver;
import marksman.shared.network.messaging.ReceivedMessage;

public final class Players implements MessageReceiver {
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
    public void receiveMessage(final ReceivedMessage message, final StringSender sender) {
        switch (message.value("event/action")) {
            case "user.updateShoots", "user.updateHits" -> {
                this.get(message.value("event/user/name")).receiveMessage(message, sender);
            }
            default -> {
                throw new RuntimeException("Unknown action: %s".formatted(message.value("event/action")));
            }
        }
    }
}
