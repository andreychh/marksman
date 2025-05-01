package marksman.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import marksman.shared.network.MessageDispatcher;

public final class FXApp implements FXController {
    private final MessageDispatcher dispatcher;
    @FXML
    private StackPane root;

    public FXApp(final MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void loginScreen(final marksman.client.login.user.User user) {
        Node node = new FXMLComponent(
                this.getClass().getResource("/marksman/client/login/user.fxml"),
                cls -> {
                    switch (cls.getName()) {
                        case "marksman.client.login.user.FXUser" -> {
                            return new marksman.client.login.user.FXUser(user);
                        }
                        default -> {
                            throw new RuntimeException("Unknown class: " + cls);
                        }
                    }
                }
        ).parent();

        Platform.runLater(() -> this.root.getChildren().setAll(node));
    }

    public void lobbyScreen(
            final marksman.client.lobby.user.User user,
            final marksman.client.lobby.players.Players players
    ) {
        this.dispatcher.addHandler("lobby.userAdded", players);
        this.dispatcher.addHandler("lobby.userRemoved", players);
        this.dispatcher.addHandler("user.readinessChanged", players);
        this.dispatcher.addHandler("user.readinessChanged", user);

        Node node = new FXMLComponent(
                this.getClass().getResource("/marksman/client/lobby/user.fxml"),
                cls -> {
                    switch (cls.getName()) {
                        case "marksman.client.lobby.user.FXUser" -> {
                            return new marksman.client.lobby.user.FXUser(user);
                        }
                        case "marksman.client.lobby.players.FXPlayers" -> {
                            return new marksman.client.lobby.players.FXPlayers(players);
                        }
                        default -> {
                            throw new RuntimeException("Unknown class: " + cls);
                        }
                    }
                }
        ).parent();

        Platform.runLater(() -> this.root.getChildren().setAll(node));
    }
}
