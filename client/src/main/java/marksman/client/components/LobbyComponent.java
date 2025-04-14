package marksman.client.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import marksman.shared.network.MessageDispatcher;
import marksman.shared.network.Message;

import java.io.IOException;
import java.io.OutputStream;

public final class LobbyComponent {
    private final OutputStream outputStream;
    private final MessageDispatcher messageDispatcher;

    @FXML
    private VBox players;

    public LobbyComponent(final OutputStream outputStream, final MessageDispatcher messageDispatcher) {
        this.outputStream = outputStream;
        this.messageDispatcher = messageDispatcher;
    }

    @FXML
    private void onReadyButtonAction(final ActionEvent actionEvent) {
        try {
            new Message()
                    .with("action", "game.ready") // todo: rename action
                    .writeTo(this.outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onExitLobbyButtonAction(final ActionEvent actionEvent) {
        try {
            new Message()
                    .with("action", "lobby.exit")
                    .writeTo(this.outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
