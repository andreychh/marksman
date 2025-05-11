package marksman.client.login.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import marksman.client.FXController;

import java.net.URL;
import java.util.ResourceBundle;

public final class FXUser implements FXController, Initializable {
    private final User user;

    @FXML
    private Button loginButton;
    @FXML
    private TextField nameTextField;

    public FXUser(final User user) {
        this.user = user;
    }

    @FXML
    private void onJoinLobbyButtonAction(final ActionEvent event) {
        try {
            this.user.rename(nameTextField.getText());
            this.user.joinLobby();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e); // todo: show error message
        }
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.nameTextField.setText(this.user.nameProperty().get());
        this.loginButton.disableProperty().bind(
                this.nameTextField.textProperty().isEmpty()
        );
        this.nameTextField.requestFocus();
    }
}
