package marksman.client.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import marksman.shared.network.Message;
import marksman.shared.network.MessageDispatcher;

import java.io.IOException;
import java.io.OutputStream;

public final class LobbyComponent {
    private final OutputStream stream;
    private final MessageDispatcher dispatcher;

    @FXML
    private VBox players;

    public LobbyComponent(final OutputStream stream, final MessageDispatcher dispatcher) {
        this.stream = stream;
        this.dispatcher = dispatcher;
    }

    @FXML
    private void onReadyButtonAction(final ActionEvent actionEvent) {
        try {
            new Message()
                    .with("action", "game.ready") // todo: rename action
                    .writeTo(this.stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onExitLobbyButtonAction(final ActionEvent actionEvent) {
        try {
            new Message()
                    .with("action", "lobby.exit")
                    .writeTo(this.stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
