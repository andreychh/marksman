module com.andreychh.marksman {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.locationtech.jts;


    exports com.andreychh.marksman.client;
    opens com.andreychh.marksman.client to javafx.fxml;
    opens com.andreychh.marksman.client.components to javafx.fxml;
}