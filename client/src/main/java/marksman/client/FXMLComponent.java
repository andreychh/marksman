package marksman.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public final class FXMLComponent {
    private final URL location;
    private final FXController controller;

    public FXMLComponent(final URL location, final FXController controller) {
        this.location = location;
        this.controller = controller;
    }

    public Parent parent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.location);
        loader.setControllerFactory(_ -> this.controller);
        return loader.load();
    }
}
