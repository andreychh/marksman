package marksman.client.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import marksman.shared.network.MapMessage;
import marksman.shared.network.MessageDispatcher;

import java.io.IOException;
import java.io.OutputStream;

public final class LoginComponent {
    private final OutputStream stream;
    private final MessageDispatcher dispatcher;

    @FXML
    private TextField nameTextField;
    @FXML
    private Label errorLabel;

    public LoginComponent(final OutputStream stream, final MessageDispatcher dispatcher) {
        this.stream = stream;
        this.dispatcher = dispatcher;
    }

    @FXML
    private void onJoinLobbyButtonAction(final ActionEvent event) {
        try {
            new MapMessage()
                    .with("action", "lobby.join")
                    .with("player.name", this.nameTextField.getText())
                    .writeTo(this.stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
