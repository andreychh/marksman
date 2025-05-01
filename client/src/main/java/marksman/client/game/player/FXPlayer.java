package marksman.client.game.player;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import marksman.client.FXController;

import java.net.URL;
import java.util.ResourceBundle;

public final class FXPlayer implements FXController, Initializable {
    private final Player player;

    @FXML
    private Label nameLabel;
    @FXML
    private Label shootsLabel;
    @FXML
    private Label hitsLabel;

    public FXPlayer(final Player player) {
        this.player = player;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.nameLabel.textProperty().bind(this.player.nameProperty());
        this.shootsLabel.textProperty().bind(this.player.shootsProperty().asString());
        this.hitsLabel.textProperty().bind(this.player.hitsProperty().asString());
    }
}
