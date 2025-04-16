package marksman.client.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import marksman.client.domain.LoginPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class LoginComponent implements Initializable {
    @FXML
    private TextField nameTextField;

    private final LoginPlayer player;

    public LoginComponent(final LoginPlayer player) {
        this.player = player;
    }

    @FXML
    private void onJoinLobbyButtonAction(final ActionEvent event) {
        try {
            this.player.joinLobby();
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: show error message
        }
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.nameTextField.textProperty().bindBidirectional(this.player.nameProperty());
    }
}
