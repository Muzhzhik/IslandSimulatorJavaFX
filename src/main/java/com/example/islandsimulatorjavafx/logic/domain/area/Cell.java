package com.example.islandsimulatorjavafx.logic.domain.area;

import com.example.islandsimulatorjavafx.logic.domain.animal.Animal;
import com.example.islandsimulatorjavafx.logic.domain.plant.Plant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
@Getter
@Setter
public class Cell {

    private int id;
    private List<Animal> animals = new CopyOnWriteArrayList<>();
    private List<Plant> plants = new CopyOnWriteArrayList<>();

    public Cell(int id) {
        this.id = id;
    }

    public boolean addPlant(Plant plant) {
        return addObject(plant);
    }

    public boolean addAnimal(Animal animal) {
        boolean result = false;
        if (addObject(animal)) {
            animal.setCurrentCellId(id);
            result = true;
        }
        return result;
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    private boolean addObject(Object o) {
        boolean result = false;
        long count = 0;
        if (o instanceof Animal) {
            count = animals.stream().filter(animal -> animal.getClass().getSimpleName().equals(o.getClass().getSimpleName())).count();
        } else if (o instanceof Plant) {
            count = plants.stream().filter(plant -> plant.getClass().getSimpleName().equals(o.getClass().getSimpleName())).count();
        }

        int maxQuantityForCell = 0;
        if (o instanceof Plant) {
            maxQuantityForCell = ((Plant) o).getMaxQuantityForCell();
        } else if (o instanceof Animal) {
            maxQuantityForCell = ((Animal) o).getMaxQuantityForCell();
        }

        if (count < maxQuantityForCell) {
            if(o instanceof Plant) {
                plants.add((Plant) o);
            } else if (o instanceof Animal) {
                animals.add((Animal) o);
            }
            result = true;
        }
        return result;
    }
}
