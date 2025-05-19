package marksman.client;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marksman.client.events.ConnectEvent;
import marksman.shared.network.connecting.Connection;
import marksman.shared.network.connecting.LoggedStringSender;
import marksman.shared.network.messaging.MessageBus;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

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
                messageBus
        );
        connection.listen();

        Scene scene = new Scene(this.root(messageBus));
        new ConnectEvent().sendTo(new LoggedStringSender(connection));

        primaryStage.setTitle("Marksman");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // todo: refactor
    private Parent root(final MessageBus messageBus) {
        FXApp root = new FXApp(messageBus);

        messageBus.addHandler("screen.changed", (message, sender) -> {
            switch (message.value("event/screen/name")) {
                case "login" -> {
                    root.loginScreen(
                            new marksman.client.login.user.User(
                                    sender,
                                    message.value("event/screen/user/name")
                            )
                    );
                }
                case "lobby" -> {
                    try {
                        root.lobbyScreen(
                                new marksman.client.lobby.user.User(
                                        sender,
                                        message.value("event/screen/user/name"),
                                        Boolean.parseBoolean(message.value("event/screen/user/readiness"))
                                ),
                                new marksman.client.lobby.players.Players(
                                        FXCollections.observableArrayList(
                                                DocumentHelper.parseText(message.value("event/screen/users"))
                                                        .selectNodes("users/user")
                                                        .stream().map(node -> new marksman.client.lobby.player.Player(
                                                                node.selectSingleNode("name").getText(),
                                                                Boolean.parseBoolean(
                                                                        node.selectSingleNode("readiness").getText()
                                                                )
                                                        ))
                                                        .toList()
                                        )
                                )
                        );
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "game" -> {
                    try {
                        root.gameScreen(
                                new marksman.client.game.user.User(
                                        sender,
                                        message.value("event/user/name")
                                ),
                                new marksman.client.game.players.Players(
                                        FXCollections.observableArrayList(
                                                DocumentHelper.parseText(message.value("event/screen/users"))
                                                        .selectNodes("users/user")
                                                        .stream()
                                                        .map(node -> new marksman.client.game.player.Player(
                                                                node.selectSingleNode("name").getText(),
                                                                Integer.parseInt(
                                                                        node.selectSingleNode("shoots").getText()
                                                                ),
                                                                Integer.parseInt(
                                                                        node.selectSingleNode("hits").getText()
                                                                )
                                                        ))
                                                        .toList()
                                        )
                                ),
                                new marksman.client.game.field.Field()
                        );
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> {
                    throw new IllegalArgumentException("Unknown screen: " + message.value("event/screen/name"));
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
