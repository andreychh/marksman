package marksman.client.lobby.user;

import javafx.beans.binding.Bindings;
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
    private Button toggleReadinessButton;

    public FXUser(final User user) {
        this.user = user;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        toggleReadinessButton.textProperty().bind(
                Bindings.when(this.user.readinessProperty())
                        .then("Unready")
                        .otherwise("Ready")
        );
    }

    @FXML
    private void onToggleReadinessButtonAction(final ActionEvent actionEvent) {
        this.user.toggleReadiness();
    }

    @FXML
    private void onLeaveLobbyButtonAction(final ActionEvent actionEvent) {
        this.user.leaveLobby();
    }
}
