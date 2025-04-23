package marksman.client.lobby.player;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public final class Component implements Initializable {
    private final Player player;

    @FXML
    private CheckBox readinessCheckBox;
    @FXML
    private Label nameLabel;

    public Component(final Player player) {
        this.player = player;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.nameLabel.textProperty().bind(this.player.nameProperty());
        this.readinessCheckBox.selectedProperty().bind(this.player.readinessProperty());
    }
}
