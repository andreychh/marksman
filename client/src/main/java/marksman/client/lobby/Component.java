package marksman.client.lobby;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class Component implements Initializable {
    private final User user;
    @FXML
    private Button toggleReadinessButton;

    public Component(final User user) {
        this.user = user;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        toggleReadinessButton.textProperty().bind(
                Bindings.when((BooleanProperty) user.readinessProperty())
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
