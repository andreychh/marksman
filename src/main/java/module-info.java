module com.andreychh.marksman {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.andreychh.marksman to javafx.fxml;
    exports com.andreychh.marksman;
}