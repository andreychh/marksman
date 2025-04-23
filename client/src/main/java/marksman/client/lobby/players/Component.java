package marksman.client.lobby.players;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import marksman.client.FXMLComponent;
import marksman.client.lobby.player.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class Component implements Initializable {
    private final Players players;

    @FXML
    private VBox playersVBox;

    public Component(final Players players) {
        this.players = players;
    }

    // todo: refactor
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        players.property().addListener((ListChangeListener<Player>) change -> {
            while (change.next()) {
                try {
                    if (change.wasAdded()) {
                        for (Player player : change.getAddedSubList()) {
                            playersVBox.getChildren().add(
                                    new FXMLComponent(
                                            getClass().getResource("/marksman/client/player.fxml"),
                                            new marksman.client.lobby.player.Component(player)
                                    ).parent()
                            );
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e); // todo: show error message
                }

                if (change.wasRemoved()) {
                    for (Player removedPlayer : change.getRemoved()) {
                        System.out.println("Removed player: " + removedPlayer.nameProperty().getValue());
                    }
                }
            }
        });
    }
}
