package com.example.islandsimulatorjavafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox mainVBox;

    @FXML
    void initialize() {
        assert mainVBox != null : "fx:id=\"mainVBox\" was not injected: check your FXML file 'main.fxml'.";
    }

}