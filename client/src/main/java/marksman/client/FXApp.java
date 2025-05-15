package marksman.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import marksman.shared.network.messaging.MessageBus;

public final class FXApp implements FXController {
    private final MessageBus messageBus;
    @FXML
    private StackPane root;

    public FXApp(final MessageBus messageBus) {
        this.messageBus = messageBus;
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
        this.messageBus.addHandler("lobby.userAdded", players);
        this.messageBus.addHandler("lobby.userRemoved", players);
        this.messageBus.addHandler("user.readinessChanged", players);
        this.messageBus.addHandler("user.readinessChanged", user);

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

    public void gameScreen(
            final marksman.client.game.user.User user,
            final marksman.client.game.players.Players players,
            final marksman.client.game.field.Field field
    ) {
        Node node = new FXMLComponent(
                this.getClass().getResource("/marksman/client/game/user.fxml"),
                cls -> {
                    switch (cls.getName()) {
                        case "marksman.client.game.user.FXUser" -> {
                            return new marksman.client.game.user.FXUser(user);
                        }
                        case "marksman.client.game.players.FXPlayers" -> {
                            return new marksman.client.game.players.FXPlayers(players);
                        }
                        case "marksman.client.game.field.FXField" -> {
                            return new marksman.client.game.field.FXField(field);
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
