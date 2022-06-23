package com.example.islandsimulatorjavafx.logic.domain.animal;

import com.example.islandsimulatorjavafx.logic.config.AnimalConfiguration;
import com.example.islandsimulatorjavafx.logic.config.Configuration;
import com.example.islandsimulatorjavafx.logic.domain.FoodChainMember;
import com.example.islandsimulatorjavafx.logic.domain.area.Area;
import com.example.islandsimulatorjavafx.logic.domain.area.Cell;
import com.example.islandsimulatorjavafx.logic.domain.plant.Plant;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
@Getter
public abstract class Animal implements FoodChainMember {
    private final String name;
    private final double weight;
    private final int maxQuantityForCell;
    private final int speed;
    private final double fullSatietyWeight;
    private int currentHungryPercent;
    private final int possibleChildQuantity;
    private final AnimalSex sex;

    Area area = Area.getInstance();
    private int currentCellId;

    protected Animal(AnimalConfiguration configuration) {
        this.name = configuration.getName();
        this.weight = configuration.getWeight();
        this.maxQuantityForCell = configuration.getMaxQuantityForCell();
        this.speed = configuration.getSpeed();
        this.currentHungryPercent = configuration.getCurrentHungryPercent();
        this.fullSatietyWeight = configuration.getFullSatietyWeight();
        this.possibleChildQuantity = configuration.getPossibleChildQuantity();
        int random = ThreadLocalRandom.current().nextInt(2);
        this.sex = random == AnimalSex.MALE.ordinal() ? AnimalSex.MALE : AnimalSex.FEMALE;
    }

    public void eat() {
        currentHungryPercent -= 1;
        Cell cell = area.getCellById(currentCellId);
        if (cell != null) {
            if (currentHungryPercent < 0) {
                //System.out.println(this.getName() + " died from hunger");
                cell.removeAnimal(this);
            } else {
                int random = ThreadLocalRandom.current().nextInt(100);
                if (random < 50) {
                    Optional<? extends FoodChainMember> optionalAnimal = cell.getAnimals().stream()
                            .filter(a -> !a.getClass().getSimpleName().equals(this.getClass().getSimpleName()))
                            .findAny();

                    optionalAnimal.ifPresent(foodChainMember -> eatAnimal((Animal) foodChainMember, cell));
                }
                if (random < 70) {
                    Optional<? extends FoodChainMember> optionalPlant = cell.getPlants().stream()
                            .findAny();

                    optionalPlant.ifPresent(foodChainMember -> eatPlant((Plant) foodChainMember, cell));
                }
            }
        }
    }

    private void eatPlant(Plant victim, Cell cell) {
        if (victim != null) {
            int random = ThreadLocalRandom.current().nextInt(100);
            if (random > 0 && random <= Configuration.getInstance().getEatChance(this, victim)) {
                if (victim.getWeight() >= this.fullSatietyWeight) {
                    this.currentHungryPercent = 100;
                } else {
                    this.currentHungryPercent += victim.getWeight();
                }
//                System.out.println(this.getName() + " eat " + victim.getName());
                victim.kill();
            }
        }
    }

    private void eatAnimal(Animal victim, Cell cell) {
        if (victim != null) {
            int random = ThreadLocalRandom.current().nextInt(100);
            if (random > 0 && random < Configuration.getInstance().getEatChance(this, victim)) {
                if (victim.getWeight() >= this.fullSatietyWeight) {
                    this.currentHungryPercent = 100;
                } else {
                    this.currentHungryPercent += victim.getWeight();
                }
//                System.out.println(this.getName() + " eat " + victim.getName());
                cell.removeAnimal(victim);
            }
        }
    }

    public void reproduce(Animal animal) {
        if (animal != null && this.getClass().getSimpleName().equals(animal.getClass().getSimpleName())
                && this.sex == AnimalSex.FEMALE && animal.sex == AnimalSex.MALE) {
            int random = ThreadLocalRandom.current().nextInt(100);
            if (random > 70) { //TODO Maybe to configuration.yaml ReproduceProbability
                for (int i = 0; i < ThreadLocalRandom.current().nextInt(possibleChildQuantity); i++) {
                    Cell cell = area.getCellById(currentCellId);
                    cell.addAnimal(new AnimalFactory().createByName(this.getClass().getSimpleName()));
//                    System.out.println(animal.getName() + " born!");
                }
            }
        }
    }

    public void relocate() {
        int random = ThreadLocalRandom.current().nextInt(100);
        if (random > 50) {
            int previousCellId = currentCellId;
            for (int cellId : getPossibleCellsToMove()) {
                if (cellId == currentCellId) {
                    continue;
                }
                Cell cell = area.getCellById(cellId);
                if (cell.addAnimal(this)) {
                    Cell previousCell = area.getCellById(previousCellId);
                    previousCell.removeAnimal(this);
                    break;
                }
            }
        }
    }

    private List<Integer> getPossibleCellsToMove() {
        int areaWidth = Configuration.getInstance().getAreaWidth();
        int areaHeight = Configuration.getInstance().getAreaHeight();
        int maxCellId = areaHeight * areaWidth;
        List<Integer> cellsPossibleToMove = new ArrayList<>();
        if (speed > 0) {
            cellsPossibleToMove.addAll(getCellsRightAndLeftFromCurrent(currentCellId, speed));
            //up
            for (int i = 1; i <= speed; i++) {
                int possibleCellId = currentCellId - areaWidth * i;
                if (possibleCellId > 0 && possibleCellId <= maxCellId) {
                    cellsPossibleToMove.add(possibleCellId);
                    cellsPossibleToMove.addAll(getCellsRightAndLeftFromCurrent(possibleCellId, speed - i));
                } else {
                    break;
                }
            }
            // down
            for (int i = 1; i <= speed; i++) {
                int possibleCellId = currentCellId + Configuration.getInstance().getAreaWidth() * i;
                if (possibleCellId > 0 && possibleCellId <= maxCellId) {
                    cellsPossibleToMove.add(possibleCellId);
                    cellsPossibleToMove.addAll(getCellsRightAndLeftFromCurrent(possibleCellId, speed - i));
                } else {
                    break;
                }
            }
        }
        Collections.shuffle(cellsPossibleToMove);
        return cellsPossibleToMove;
    }

    private List<Integer> getCellsRightAndLeftFromCurrent(int currentCellId, int count) {
        int areaWidth = Configuration.getInstance().getAreaWidth();
        int areaHeight = Configuration.getInstance().getAreaHeight();
        int maxCellId = areaHeight * areaWidth;
        List<Integer> cells = new ArrayList<>();
        if (count > 0) {
            //To right
            for (int i = 1; i <= count; i++) {
                int cellId = currentCellId + i;
                if (cellId > 0 && cellId <= maxCellId) {
                    cells.add(cellId);
                } else {
                    break;
                }
            }
            //To left
            for (int i = 1; i <= count; i++) {
                int cellId = currentCellId - i;
                if (cellId > 0 && cellId <= maxCellId) {
                    cells.add(cellId);
                } else {
                    break;
                }
            }
        }
        return cells;
    }

    public void setCurrentCellId(int currentCellId) {
        this.currentCellId = currentCellId;
    }
}
