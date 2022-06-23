package com.example.islandsimulatorjavafx.logic.domain.area;

import com.example.islandsimulatorjavafx.logic.config.Configuration;
import com.example.islandsimulatorjavafx.logic.domain.animal.Animal;
import com.example.islandsimulatorjavafx.logic.domain.plant.Plant;
import com.example.islandsimulatorjavafx.logic.objectgeneration.AnimalGenerator;
import com.example.islandsimulatorjavafx.logic.objectgeneration.PlantGenerator;
import lombok.Getter;

import java.util.*;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
@Getter
public class Area {

    private static Area area;
    Configuration configuration = Configuration.getInstance();
    private final Cell[][] cells = new Cell[configuration.getAreaHeight()][configuration.getAreaWidth()];
//    private int[][] cellsIdTable;

    private Area() {};

    public static Area getInstance() {
        if (area == null) {
            area = new Area();
            area.createArea();
        }
        return area;
    }

    private void createArea() {
        initCells();
        addAnimals();
        addPlants();
       // getCellIdTable();
    }

    private void initCells() {
        int counter = 1;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(counter++);
            }
        }
    }

    private void addAnimals() {
        System.out.println("Adding animals");
        Queue<Animal> animals = new LinkedList<>(new AnimalGenerator().generate());
        List<Cell> cellList = shuffleCells();
        while(!animals.isEmpty()) {
            for (Cell cell : cellList) {
                if (animals.isEmpty()) {
                    break;
                }
                addRandomAnimalQuantity(cell, animals);
            }
        }
    }

    private void addRandomAnimalQuantity(Cell cell, Queue<Animal> animals) {
        Random random = new Random();
        int randomInt = random.nextInt(10);
        if (animals.size() < 3) {
            randomInt = animals.size();
        }

        for (int i = 0; i < randomInt; i++) {
            if (animals.isEmpty()) {
                break;
            }
            Animal animal = animals.peek();
            if (cell.addAnimal(animal)){
                animals.remove();
            } else {
                break;
            }
        }
    }

    private void addPlants() {
        System.out.println("Adding plants");
        Queue<Plant> plants = new LinkedList<>(new PlantGenerator().generate());
        List<Cell> cellList = shuffleCells();
        while(!plants.isEmpty()) {
            for (Cell cell : cellList) {
                if (plants.isEmpty()) {
                    break;
                }
                addRandomPlantsQuantity(cell, plants);
            }
        }
    }

    private void addRandomPlantsQuantity(Cell cell, Queue<Plant> plants) {
        Random random = new Random();
        int randomInt = random.nextInt(10);
        if (plants.size() < 3) {
            randomInt = plants.size();
        }

        for (int i = 0; i < randomInt; i++) {
            if (plants.isEmpty()) {
                break;
            }
            Plant plant = plants.peek();
            if (cell.addPlant(plant)){
                plants.remove();
            } else {
                break;
            }
        }
    }

    private List<Cell> shuffleCells() {
        List<Cell> cellList = new ArrayList<>();
        for(Cell[] cells : cells) {
            cellList.addAll(Arrays.asList(cells));
        }
        Collections.shuffle(cellList);
        return cellList;
    }

    public synchronized Cell getCellById(int cellId) {
        Cell result = null;
        int areaWidth = Configuration.getInstance().getAreaWidth();
        int startRow = (int) Math.floor((cellId > 1 ? (cellId - 1) : cellId) * 1.0 / areaWidth);
        synchronized (cells) {
            for (int i = 0; i < areaWidth; i++) {
                Cell element = cells[startRow][i];
                if (cellId == element.getId()) {
                    result = element;
                    break;
                }
            }
        }
        if (result == null) {
            System.err.println("Cell.getCellById() has null result. CellId=" + cellId);
        }
        return result;
    }

//    public int[][] getCellIdTable() {
//        if (cellsIdTable == null) {
//            int height = cells.length;
//            int width = cells[0].length;
//            cellsIdTable = new int[height][width];
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    cellsIdTable[i][j] = cells[i][j].getId();
////                    String sCellId = cellsIdTable[i][j] + "";
////                    if (sCellId.length() == 1) {
////                        sCellId = " " + sCellId + " ";
////                    } else if (sCellId.length() == 2) {
////                        sCellId = " " + sCellId;
////                    }
////                    System.out.print(sCellId + " ");
//                }
////                System.out.println("");
//            }
//        }
//        return cellsIdTable;
//    }
}
