package com.example.islandsimulatorjavafx;

import com.example.islandsimulatorjavafx.logic.config.Configuration;
import com.example.islandsimulatorjavafx.logic.controller.SimulationController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class IslandSimulator extends Application {

    int areaWidth = 21;
    int areaHeight = 22;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(createMainWindowScene());
        stage.setTitle("Island Simulator");
        stage.show();
    }

    private Scene createMainWindowScene() {
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        GridPane gridPane = createCellsField();
        scrollPane.setContent(gridPane);
        vBox.getChildren().add(0, createMenu());
        vBox.getChildren().add(1, gridPane);
        Scene scene = new Scene(vBox, areaWidth * 41, areaWidth * 28);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        return scene;
    }

    private GridPane createCellsField() {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
                Button button = new Button(i + "" + j);
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);
                gridPane.add(button, j, i);
            }
        }
        return gridPane;
    }

    private MenuBar createMenu() {
        Menu menu = new Menu("Simulation");
        MenuItem menuItem1 = new MenuItem("Start");
        MenuItem menuItem2 = new MenuItem("Stop");
        menu.getItems().add(menuItem1);
        menu.getItems().add(menuItem2);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        return menuBar;
    }

    public static void main(String[] args) {
        SimulationController simulationController = new SimulationController();
        simulationController.start();
        try {
            Thread.sleep(Configuration.getInstance().getSimulationDuration() * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            simulationController.stop();
        }
        launch();
    }
}