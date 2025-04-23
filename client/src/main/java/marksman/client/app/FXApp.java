package marksman.client.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import marksman.client.FXController;
import marksman.client.FXMLComponent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class FXApp implements FXController, Initializable {
    private final App app;
    private final Users users;

    @FXML
    private StackPane root;

    public FXApp(final App app, final Users users) {
        this.app = app;
        this.users = users;
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        app.screenProperty().addListener((_, _, screen) -> this.updateScreen(screen));
        this.updateScreen(app.screenProperty().get());
    }

    public void updateScreen(final String screen) {
        try {
            this.root.getChildren().clear();
            this.root.getChildren().add(this.loadScreen(screen).parent());
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: show error message
        }
    }

    private FXMLComponent loadScreen(final String screen) {
        return switch (screen) {
            case "login" -> this.loadLoginScreen();
            case "lobby" -> this.loadLobbyScreen();
            default -> throw new IllegalArgumentException("Unknown screen: " + screen);
        };
    }

    private FXMLComponent loadLoginScreen() {
        return new FXMLComponent(
                getClass().getResource("/marksman/client/login/user.fxml"),
                new marksman.client.login.user.FXUser(this.users.loginUser())
        );
    }

    private FXMLComponent loadLobbyScreen() {
        return new FXMLComponent(
                getClass().getResource("/marksman/client/lobby/user.fxml"),
                new marksman.client.lobby.user.FXUser(this.users.lobbyUser())
        );
    }
}
