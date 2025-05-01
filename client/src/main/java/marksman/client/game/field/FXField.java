package marksman.client.game.field;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import marksman.client.FXController;

import java.net.URL;
import java.util.ResourceBundle;

public final class FXField implements FXController, Initializable {
    private final Field field;

    @FXML
    private Pane fieldPane;

    public FXField(final Field field) {
        this.field = field;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }
}
