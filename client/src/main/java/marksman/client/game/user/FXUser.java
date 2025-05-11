package marksman.client.game.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import marksman.client.FXController;

import java.net.URL;
import java.util.ResourceBundle;

public final class FXUser implements FXController, Initializable {
    private final User user;

    @FXML
    private Button fireButton;
    @FXML
    private Button pauseButton;

    public FXUser(final User user) {
        this.user = user;
    }

    @FXML
    private void onFireButtonAction(final ActionEvent event) {
        this.user.fire();
    }

    @FXML
    private void onPauseButtonAction(final ActionEvent event) {
        this.user.pauseGame();
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {

    }
}
