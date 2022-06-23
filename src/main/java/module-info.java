module com.example.islandsimulatorjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.example.islandsimulatorjavafx to javafx.fxml;
    exports com.example.islandsimulatorjavafx;
}