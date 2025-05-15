package marksman.client;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marksman.shared.network.connecting.Connection;
import marksman.shared.network.messaging.LoggedMessageReceiver;
import marksman.shared.network.messaging.Message;
import marksman.shared.network.messaging.MessageBus;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public final class Application extends javafx.application.Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        MessageBus messageBus = new MessageBus();
        Connection connection = new Connection(
                this.connect(new InetSocketAddress("localhost", 12345), 5, 5000),
                new LoggedMessageReceiver(messageBus)
        );
        connection.start();

        Scene scene = new Scene(this.root(messageBus));
        connection.sendMessage(new Message()
                .with("action", "app.connect"));

        primaryStage.setTitle("Marksman");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // todo: refactor
    private Parent root(final MessageBus messageBus) {
        FXApp root = new FXApp(messageBus);

        messageBus.addHandler("app.screenChanged", (message, connection) -> {
            switch (message.value("screen.name")) {
                case "login" -> {
                    root.loginScreen(
                            new marksman.client.login.user.User(
                                    connection,
                                    message.value("user.name")
                            )
                    );
                }
                case "lobby" -> {
                    root.lobbyScreen(
                            new marksman.client.lobby.user.User(
                                    connection,
                                    new SimpleStringProperty(message.value("user.name")),
                                    new SimpleBooleanProperty(Boolean.parseBoolean(message.value("user.readiness")))
                            ),
                            new marksman.client.lobby.players.Players(
                                    FXCollections.observableArrayList(
                                            new marksman.client.lobby.players.InputAsPlayers(
                                                    message.value("lobby.users")
                                            ).players()
                                    )
                            )
                    );
                }
                case "game" -> {
                    root.gameScreen(
                            new marksman.client.game.user.User(
                                    connection,
                                    new SimpleStringProperty(message.value("user.name"))
                            ),
                            new marksman.client.game.players.Players(
                                    FXCollections.observableArrayList(
                                            new marksman.client.game.players.InputAsPlayers(
                                                    message.value("game.users")
                                            ).players()
                                    )
                            ),
                            new marksman.client.game.field.Field()
                    );
                }
                default -> {
                    throw new IllegalArgumentException("Unknown screen: " + message.value("screen.name"));
                }
            }
        });

        return new FXMLComponent(this.getClass().getResource("/marksman/client/app/app.fxml"), root).parent();
    }

    private Socket connect(final SocketAddress address, final int attempts, final int timeout) {
        for (int a = 0; a < attempts; a++) {
            try {
                Socket socket = new Socket();
                socket.connect(address);
                return socket;
            } catch (IOException e) {
                System.out.println("Connection attempt " + (a + 1) + " failed");
            }
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Connection attempt interrupted", e);
            }
        }
        throw new RuntimeException("Failed to connect after " + attempts + " attempts");
    }
}
