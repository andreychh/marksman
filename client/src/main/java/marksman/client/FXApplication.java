package marksman.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import marksman.client.components.LobbyComponent;
import marksman.client.components.LoginComponent;
import marksman.client.components.RootComponent;
import marksman.client.domain.LoginPlayer;
import marksman.shared.network.Connection;
import marksman.shared.network.MessageDispatcher;

import java.io.IOException;
import java.net.Socket;

public final class FXApplication extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        MessageDispatcher dispatcher = new MessageDispatcher();
        Connection connection = new Connection(new Socket("localhost", 12345), dispatcher);
        connection.start();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/marksman/client/root.fxml"));
        loader.setControllerFactory(cls -> {
            try {
                return switch (cls.getSimpleName()) {
                    case "RootComponent" -> new RootComponent(connection.outputStream(), dispatcher);
                    case "LoginComponent" -> new LoginComponent(new LoginPlayer(connection.outputStream()));
                    case "LobbyComponent" -> new LobbyComponent(connection.outputStream(), dispatcher);
                    default -> throw new RuntimeException("Unknown class: " + cls);
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
}
