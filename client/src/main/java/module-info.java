module marksman.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires marksman.shared;

    opens marksman.client to javafx.fxml;

    opens marksman.client.login.user to javafx.fxml;

    opens marksman.client.lobby.user to javafx.fxml;
    opens marksman.client.lobby.player to javafx.fxml;
    opens marksman.client.lobby.players to javafx.fxml;

    opens marksman.client.game.user to javafx.fxml;
    opens marksman.client.game.players to javafx.fxml;
    opens marksman.client.game.player to javafx.fxml;
    opens marksman.client.game.field to javafx.fxml;

    exports marksman.client;
}
