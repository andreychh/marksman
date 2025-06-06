package marksman.client.game.players;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import marksman.client.FXController;
import marksman.client.FXMLComponent;
import marksman.client.game.player.FXPlayer;
import marksman.client.game.player.Player;

import java.net.URL;
import java.util.ResourceBundle;

public final class FXPlayers implements FXController, Initializable {
    private final Players players;

    @FXML
    private VBox playersVBox;

    public FXPlayers(final Players players) {
        this.players = players;
    }

    // todo: refactor
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            players.listProperty().forEach(p -> {
                playersVBox.getChildren().add(this.playerNode(p));
            });
        });

        players.listProperty().addListener((ListChangeListener<Player>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> {
                        change.getAddedSubList().forEach(p -> {
                            playersVBox.getChildren().add(this.playerNode(p));
                        });
                    });
                }

                if (change.wasRemoved()) {
                    Platform.runLater(() -> {
                        change.getRemoved().forEach(p -> {
                            for (Node n : playersVBox.getChildren()) {
                                if (n.getUserData() == p.nameProperty().get()) {
                                    playersVBox.getChildren().remove(n);
                                    break;
                                }
                            }
                        });
                    });
                }
            }
        });
    }

    private Node playerNode(final Player player) {
        Node node = new FXMLComponent(
                getClass().getResource("/marksman/client/game/player.fxml"),
                new FXPlayer(player)
        ).parent();
        node.setUserData(player.nameProperty().get());
        return node;
    }
}
