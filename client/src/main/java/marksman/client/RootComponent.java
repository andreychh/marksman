package marksman.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import marksman.shared.network.Message;
import marksman.shared.network.MessageDispatcher;

import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

// todo: redesign
public final class RootComponent implements Initializable {
    private final OutputStream stream;
    private final MessageDispatcher dispatcher;

    @FXML
    private BorderPane loginComponent;
    @FXML
    private BorderPane lobbyComponent;

    public RootComponent(final OutputStream stream, final MessageDispatcher dispatcher) {
        this.stream = stream;
        this.dispatcher = dispatcher;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.dispatcher.addHandler("lobby.joined", this::onLobbyEnter);
    }

    private void onLobbyEnter(final Message message, final OutputStream stream) {
        lobbyComponent.setVisible(true);
        loginComponent.setVisible(false);
    }
}
