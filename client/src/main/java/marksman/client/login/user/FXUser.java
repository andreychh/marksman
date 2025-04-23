package marksman.client.login.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import marksman.client.FXController;

import java.io.IOException;

public final class FXUser implements FXController {
    private final User user;

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
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: show error message
        }
    }
}
