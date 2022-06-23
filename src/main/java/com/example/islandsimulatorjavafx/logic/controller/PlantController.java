package com.example.islandsimulatorjavafx.logic.controller;

import com.example.islandsimulatorjavafx.logic.config.Configuration;
import com.example.islandsimulatorjavafx.logic.domain.area.Area;
import com.example.islandsimulatorjavafx.logic.domain.area.Cell;
import com.example.islandsimulatorjavafx.logic.domain.plant.Plant;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
public class PlantController implements Runnable {

    Area area = Area.getInstance();

    @Override
    public void run() {
        while (!SimulationController.simulationEnd) {
            System.out.println("Plant controller works");
            sleep();
            checkAllPlants();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // Do nothing
        }
    }

    private void checkAllPlants() {
        for (Cell[] cells : area.getCells()) {
            for (Cell cell : cells) {
                checkCellPlants(cell);
            }
        }
    }

    private void checkCellPlants(Cell currentCell) {
        List<Plant> plants = currentCell.getPlants();
        if (plants != null && !plants.isEmpty()) {
            for (Plant plant : plants) {
                tryToReviveInOtherCell(plant, currentCell);
                plant.killOrRevive();
            }
            Collections.shuffle(plants);
        }
    }

    private void tryToReviveInOtherCell(Plant plant, Cell currentCell) {
        final int chance = 10; //TODO Move to configuration.yaml
        int random = ThreadLocalRandom.current().nextInt(1000);
        if (random < chance) {
            movePlantToAnotherCell(plant, currentCell);
        }
    }

    private void movePlantToAnotherCell(Plant plant, Cell currentCell) {
        final int maxCellId = Configuration.getInstance().getAreaHeight() * Configuration.getInstance().getAreaHeight();
        int randomCellId = ThreadLocalRandom.current().nextInt(maxCellId);
        if (randomCellId != currentCell.getId()) {
            Cell cell = area.getCellById(++randomCellId);
            if (cell != null && cell.addPlant(plant)) {
                currentCell.removePlant(plant);
            }
        }
    }
}
