package marksman.client.lobby.user;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import marksman.client.FXController;
import marksman.client.FXMLComponent;
import marksman.client.lobby.players.FXPlayers;
import marksman.client.lobby.players.Players;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class FXUser implements FXController, Initializable {
    private final User user;

    @FXML
    private BorderPane root;
    @FXML
    private Button toggleReadinessButton;

    public FXUser(final User user) {
        this.user = user;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        try {
            this.root.getChildren().add(
                    new FXMLComponent(
                            this.getClass().getResource("/marksman/client/login/players.fxml"),
                            new FXPlayers(new Players(FXCollections.observableArrayList()))
                    ).parent()
            );
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: show error message
        }
        toggleReadinessButton.textProperty().bind(
                Bindings.when(this.user.readinessProperty())
                        .then("Unready")
                        .otherwise("Ready")
        );
    }

    @FXML
    private void onToggleReadinessButtonAction(final ActionEvent actionEvent) {
        try {
            this.user.toggleReadiness();
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: show error message
        }
    }

    @FXML
    private void onLeaveLobbyButtonAction(final ActionEvent actionEvent) {
        try {
            this.user.leaveLobby();
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: show error message
        }
    }
}
