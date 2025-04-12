package marksman.client.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import marksman.client.MessageDispatcher;
import marksman.shared.network.Message;

import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public final class RootComponent implements Initializable {
    private final OutputStream outputStream;
    private final MessageDispatcher messageDispatcher;

    @FXML
    private BorderPane loginComponent;
    @FXML
    private BorderPane lobbyComponent;

    public RootComponent(final OutputStream outputStream, final MessageDispatcher messageDispatcher) {
        this.outputStream = outputStream;
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.messageDispatcher.addHandler("lobby.joined", this::onLobbyEnter);
    }

    private void onLobbyEnter(final Message message, final OutputStream outputStream) {
        lobbyComponent.setVisible(true);
        loginComponent.setVisible(false);
    }
}
