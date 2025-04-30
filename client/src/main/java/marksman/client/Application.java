package marksman.client;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marksman.shared.network.Connection;
import marksman.shared.network.LoggedMessageHandler;
import marksman.shared.network.Message;
import marksman.shared.network.MessageDispatcher;

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
        MessageDispatcher dispatcher = new MessageDispatcher();
        Connection connection = new Connection(
                this.connect(new InetSocketAddress("localhost", 12345), 5, 5000),
                new LoggedMessageHandler(dispatcher)
        );
        connection.start();

        Scene scene = new Scene(this.root(dispatcher));
        new Message().with("action", "app.connect").writeTo(connection.outputStream());

        primaryStage.setTitle("Marksman");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // todo: refactor
    private Parent root(final MessageDispatcher dispatcher) {
        FXApp root = new FXApp(dispatcher);

        dispatcher.addHandler("app.screenChanged", (message, stream) -> {
            switch (message.value("screen.name")) {
                case "login" -> {
                    root.loginScreen(
                            new marksman.client.login.user.User(
                                    stream,
                                    new SimpleStringProperty(message.value("user.name"))
                            )
                    );
                }

                case "lobby" -> {
                    root.lobbyScreen(
                            new marksman.client.lobby.user.User(
                                    stream,
                                    new SimpleStringProperty(message.value("user.name")),
                                    new SimpleBooleanProperty(Boolean.parseBoolean(message.value("user.readiness")))
                            ),
                            new marksman.client.lobby.players.Players(FXCollections.observableArrayList())
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
