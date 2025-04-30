package marksman.client.lobby.players;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import marksman.client.lobby.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class InputAsPlayers {
    private final String input;

    public InputAsPlayers(final String input) {
        this.input = input;
    }

    public List<Player> players() {
        List<Player> players = new ArrayList<>();
        if (Objects.equals(this.input, "null")) {
            return players;
        }
        String[] inputs = this.input.split("%");
        for (String player : inputs) {
            String[] fields = player.split("~");
            players.add(new Player(
                    new SimpleStringProperty(fields[0]),
                    new SimpleBooleanProperty(Boolean.parseBoolean(fields[1]))
            ));
        }
        return players;
    }
}
