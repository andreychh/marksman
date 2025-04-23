package marksman.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marksman.client.lobby.User;
import marksman.client.lobby.player.Player;
import marksman.client.lobby.players.Players;
import marksman.client.login.Component;
import marksman.shared.network.Connection;
import marksman.shared.network.MessageDispatcher;

import java.io.IOException;
import java.net.Socket;

public final class FXApplication extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    // todo: refactor
    @Override
    public void start(final Stage primaryStage) throws IOException {
        MessageDispatcher dispatcher = new MessageDispatcher();
        Connection connection = new Connection(new Socket("localhost", 12345), dispatcher);
        connection.start();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/marksman/client/root.fxml"));
        loader.setControllerFactory(cls -> {
            Property<String> userNameProperty = new SimpleStringProperty();
            try {
                return switch (cls.getName()) {
                    case "marksman.client.RootComponent" -> new RootComponent(
                            connection.outputStream(),
                            dispatcher
                    );
                    case "marksman.client.login.Component" -> new Component(
                            new marksman.client.login.User(
                                    connection.outputStream(),
                                    userNameProperty
                            )
                    );
                    case "marksman.client.lobby.Component" -> new marksman.client.lobby.Component(
                            new User(
                                    connection.outputStream(),
                                    userNameProperty,
                                    new SimpleBooleanProperty(false)
                            )
                    );
                    case "marksman.client.lobby.players.Component" -> new marksman.client.lobby.players.Component(
                            this.getPlayers(dispatcher)
                    );
                    default -> throw new RuntimeException("Unknown class: " + cls.getName());
                };
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        primaryStage.setOnCloseRequest(event -> {
            try {
                connection.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        primaryStage.setTitle("Marksman");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    private Players getPlayers(final MessageDispatcher dispatcher) {
        Players players = new Players(FXCollections.observableArrayList());
        dispatcher.addHandler("players.added", (message, stream) -> {
            Platform.runLater(() -> players.add(
                    new Player(
                            new SimpleStringProperty(message.value("player.name")),
                            new SimpleBooleanProperty(false)
                    )
            ));
        });
        return players;
    }
}
