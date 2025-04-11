package com.andreychh.marksman.client.components;

import com.andreychh.marksman.client.MessageDispatcher;
import com.andreychh.marksman.common.network.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.OutputStream;

public final class LoginComponent {
    private final OutputStream outputStream;
    private final MessageDispatcher messageDispatcher;

    @FXML
    private TextField nameTextField;
    private Label errorLabel;

    public LoginComponent(final OutputStream outputStream, final MessageDispatcher messageDispatcher) {
        this.outputStream = outputStream;
        this.messageDispatcher = messageDispatcher;
    }

    @FXML
    private void onJoinLobbyButtonAction(final ActionEvent event) {
        try {
            new Message()
                    .with("action", "lobby.join")
                    .with("player.name", this.nameTextFiled.getText())
                    .writeTo(this.outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
