package marksman.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public final class FXMLComponent implements FXComponent {
    private final URL location;
    private final Object controller;

    // todo: add FXController interface
    public FXMLComponent(final URL location, final Object controller) {
        this.location = location;
        this.controller = controller;
    }

    @Override
    public Parent parent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.location);
        loader.setControllerFactory(_ -> this.controller);
        return loader.load();
    }
}
