package com.andreychh.marksman.client;

import com.andreychh.marksman.client.components.LobbyComponent;
import com.andreychh.marksman.client.components.LoginComponent;
import com.andreychh.marksman.client.components.RootComponent;
import com.andreychh.marksman.common.network.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public final class FXApplication extends Application {

    @Override
    public void start(final Stage primaryStage) throws IOException {
        MessageDispatcher messageDispatcher = new MessageDispatcher();
        Session session = new Session(new Socket("localhost", 12345), messageDispatcher);
        session.start();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/andreychh/marksman/client/root.fxml"));
        loader.setControllerFactory(cls -> {
            try {
                return switch (cls.getSimpleName()) {
                    case "RootComponent" -> new RootComponent(session.outputStream(), messageDispatcher);
                    case "LoginComponent" -> new LoginComponent(session.outputStream(), messageDispatcher);
                    case "LobbyComponent" -> new LobbyComponent(session.outputStream(), messageDispatcher);
                    default -> throw new RuntimeException("Unknown class: " + cls);
                };
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        primaryStage.setOnCloseRequest(event -> {
            try {
                session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        primaryStage.setTitle("Marksman");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
