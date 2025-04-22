package marksman.client.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public final class Component {
    @FXML
    private TextField nameTextField;

    private final User user;

    public Component(final User user) {
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
