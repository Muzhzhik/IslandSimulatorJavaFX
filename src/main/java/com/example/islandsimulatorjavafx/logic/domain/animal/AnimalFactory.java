package com.example.islandsimulatorjavafx.logic.domain.animal;

import com.example.islandsimulatorjavafx.logic.config.Configuration;
import com.example.islandsimulatorjavafx.logic.domain.animal.herbivore.*;
import com.example.islandsimulatorjavafx.logic.domain.animal.predator.*;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
public class AnimalFactory {

    Configuration configuration = Configuration.getInstance();

    public Animal createByName(String className) {
        if (Boar.class.getSimpleName().equals(className)) {
            return new Boar(configuration.getAnimalConfigurationByName(className));
        } else if (Buffalo.class.getSimpleName().equals(className)) {
            return new Buffalo(configuration.getAnimalConfigurationByName(className));
        } else if (Caterpillar.class.getSimpleName().equals(className)) {
            return new Caterpillar(configuration.getAnimalConfigurationByName(className));
        } else if (Deer.class.getSimpleName().equals(className)) {
            return new Deer(configuration.getAnimalConfigurationByName(className));
        } else if (Duck.class.getSimpleName().equals(className)) {
            return new Duck(configuration.getAnimalConfigurationByName(className));
        } else if (Goat.class.getSimpleName().equals(className)) {
            return new Goat(configuration.getAnimalConfigurationByName(className));
        } else if (Horse.class.getSimpleName().equals(className)) {
            return new Horse(configuration.getAnimalConfigurationByName(className));
        } else if (Mouse.class.getSimpleName().equals(className)) {
            return new Mouse(configuration.getAnimalConfigurationByName(className));
        } else if (Rabbit.class.getSimpleName().equals(className)) {
            return new Rabbit(configuration.getAnimalConfigurationByName(className));
        } else if (Sheep.class.getSimpleName().equals(className)) {
            return new Sheep(configuration.getAnimalConfigurationByName(className));
        } else if (Bear.class.getSimpleName().equals(className)) {
            return new Bear(configuration.getAnimalConfigurationByName(className));
        } else if (Boa.class.getSimpleName().equals(className)) {
            return new Boa(configuration.getAnimalConfigurationByName(className));
        } else if (Eagle.class.getSimpleName().equals(className)) {
            return new Eagle(configuration.getAnimalConfigurationByName(className));
        } else if (Fox.class.getSimpleName().equals(className)) {
            return new Fox(configuration.getAnimalConfigurationByName(className));
        } else if (Wolf.class.getSimpleName().equals(className)) {
            return new Wolf(configuration.getAnimalConfigurationByName(className));
        } else {
            System.err.println("Cant create animal. Cant find class " + className + " in fabric.");
            return null;
        }
    }
}
