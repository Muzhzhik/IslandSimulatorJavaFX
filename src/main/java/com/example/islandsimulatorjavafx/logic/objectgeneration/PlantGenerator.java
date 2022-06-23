package com.example.islandsimulatorjavafx.logic.objectgeneration;

import com.example.islandsimulatorjavafx.logic.config.Configuration;
import com.example.islandsimulatorjavafx.logic.config.PlantConfiguration;
import com.example.islandsimulatorjavafx.logic.domain.plant.Plant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
public class PlantGenerator {

    Configuration configuration = Configuration.getInstance();

    public List<Plant> generate() {
        List<Plant> plants = new ArrayList<>();
        PlantConfiguration plantConfiguration = configuration.getPlantConfigurationByName(Plant.class.getSimpleName());
        long maxQuantity = (long) configuration.getAreaWidth() * configuration.getAreaHeight() * plantConfiguration.getMaxQuantityForCell();
        long cycleLimit = maxQuantity > configuration.getPlantQuantity() ? configuration.getPlantQuantity() : maxQuantity;
        configuration.setPlantQuantity((int) cycleLimit);
        for (int i = 0; i < cycleLimit; i++) {
            plants.add(new Plant(plantConfiguration));
        }
        return plants;
    }

}
