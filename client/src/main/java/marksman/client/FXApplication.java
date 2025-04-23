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
import marksman.client.app.FXApp;
import marksman.client.lobby.player.Player;
import marksman.client.lobby.players.FXPlayers;
import marksman.client.lobby.players.Players;
import marksman.client.lobby.user.FXUser;
import marksman.client.lobby.user.User;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/marksman/client/app/app.fxml"));
        loader.setControllerFactory(cls -> {
            Property<String> userNameProperty = new SimpleStringProperty();
            try {
                return switch (cls.getName()) {
                    case "marksman.client.app.FXApp" -> new FXApp(
                            connection.outputStream(),
                            dispatcher
                    );
                    case "marksman.client.login.user.FXUser" -> new marksman.client.login.user.FXUser(
                            new marksman.client.login.user.User(
                                    connection.outputStream(),
                                    userNameProperty
                            )
                    );
                    case "marksman.client.lobby.user.FXUser" -> new FXUser(
                            new User(
                                    connection.outputStream(),
                                    userNameProperty,
                                    new SimpleBooleanProperty(false)
                            )
                    );
                    case "marksman.client.lobby.players.FXPlayers" -> new FXPlayers(
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
