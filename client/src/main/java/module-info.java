module marksman.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires marksman.shared;

    opens marksman.client to javafx.fxml;
    opens marksman.client.components to javafx.fxml;

    exports marksman.client;
}
