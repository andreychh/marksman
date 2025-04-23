package marksman.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marksman.client.app.App;
import marksman.client.app.FXApp;
import marksman.client.app.Users;
import marksman.client.lobby.player.Player;
import marksman.client.lobby.players.Players;
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

        Users users = new Users(connection.outputStream(), new SimpleStringProperty(""));

        FXApp fxApp = new FXApp(new App(new SimpleStringProperty("login")), users);

        dispatcher.addHandler("lobby.joined", (message, stream) -> Platform.runLater(() -> {
            fxApp.updateScreen("lobby");
        }));

        FXMLComponent app = new FXMLComponent(getClass().getResource("/marksman/client/app/app.fxml"), fxApp);
        primaryStage.setTitle("Marksman");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(app.parent()));
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
