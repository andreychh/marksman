module marksman.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires marksman.shared;

    opens marksman.client to javafx.fxml;

    exports marksman.client;
    opens marksman.client.lobby to javafx.fxml;
    opens marksman.client.login to javafx.fxml;
    opens marksman.client.lobby.players to javafx.fxml;
    opens marksman.client.lobby.player to javafx.fxml;
}
