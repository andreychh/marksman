package marksman.client.game.players;

import marksman.client.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public final class InputAsPlayers {
    private final String input;

    public InputAsPlayers(final String input) {
        this.input = input;
    }

    public List<Player> players() {
        return new ArrayList<>();
    }
}
