package com.example.islandsimulatorjavafx.logic.controller;

import com.example.islandsimulatorjavafx.logic.domain.animal.Animal;
import com.example.islandsimulatorjavafx.logic.domain.area.Area;
import com.example.islandsimulatorjavafx.logic.domain.area.Cell;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
public class AnimalController implements Runnable {

    Area area = Area.getInstance();

    @Override
    public void run() {
        while (!SimulationController.simulationEnd) {
            System.out.println("Animal controller works");
            sleep();
            for (Cell[] cells : area.getCells()) {
                for (Cell cell : cells) {
                    List<Animal> animals = cell.getAnimals();
                    for (Animal animal : animals) {
                        animal.eat();
                        animal.reproduce(getAnimalPair(animals, animal));
                        animal.relocate();
                    }
                }
            }
        }
    }

    private Animal getAnimalPair(List<Animal> animals, Animal currentAnimal) {
        Animal pair = null;
        Optional<Animal> animalPair = animals.stream()
                .filter(a -> a.getClass().getSimpleName().equals(currentAnimal.getClass().getSimpleName()))
                .filter(a -> a != currentAnimal)
                .collect(Collectors.toList()).stream().findAny();
        if (animalPair.isPresent()) {
            pair = animalPair.get();
        }
        return pair;
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}
