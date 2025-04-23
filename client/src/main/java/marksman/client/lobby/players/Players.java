package marksman.client.lobby.players;

import javafx.collections.ObservableList;
import marksman.client.lobby.player.Player;

public final class Players {
    private final ObservableList<Player> players;

    public Players(final ObservableList<Player> players) {
        this.players = players;
    }

    public void add(final Player player) {
        this.players.add(player);
    }

    public void remove(final Player player) {
        this.players.remove(player);
    }

    public ObservableList<Player> listProperty() {
        return players;
    }
}
