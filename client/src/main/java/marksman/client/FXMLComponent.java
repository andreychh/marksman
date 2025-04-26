package marksman.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

public final class FXMLComponent {
    private final URL location;
    private final Callback<Class<?>, FXController> controllerFactory;

    public FXMLComponent(final URL location, final Callback<Class<?>, FXController> controllerFactory) {
        this.location = location;
        this.controllerFactory = controllerFactory;
    }

    public FXMLComponent(final URL location, final FXController controller) {
        this(location, _ -> controller);
    }

    public Parent parent() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.location);
            loader.setControllerFactory(this.controllerFactory::call);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
