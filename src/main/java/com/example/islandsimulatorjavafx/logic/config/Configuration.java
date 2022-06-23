package com.example.islandsimulatorjavafx.logic.config;


import com.example.islandsimulatorjavafx.logic.domain.FoodChainMember;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.Getter;
import lombok.Setter;
import com.example.islandsimulatorjavafx.logic.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
@Getter
@Setter
public class Configuration {

    private static Configuration configuration;

    // Field Properties
    private int areaHeight;
    private int areaWidth;

    // Plants Properties
    private int plantQuantity;

    // Other Properties
    private int simulationDuration; //seconds

    private List<PlantConfiguration> plantConfigurations;
    private List<AnimalConfiguration> animalConfigurations;
    private List<List<String>> animalInteractionList;
    private Map<String, Map<String, Integer>> animalInteractionTable = new HashMap<>();

    private Configuration() {
    }

    public static Configuration getInstance() {
        if (configuration == null) {
            ObjectMapper mapper = new YAMLMapper();
            try {
                configuration = mapper.readValue(new File(Constants.APP_CONFIGURATION_PATH),
                        Configuration.class);
            } catch (FileNotFoundException e) {
                System.err.println("Cant load configuration. File not found.");
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Cant load configuration. IO error happened.");
                e.printStackTrace();
                System.exit(0);
            }
        }
        return configuration;
    }

    public PlantConfiguration getPlantConfigurationByName(String className) { //TODO Stream()
        PlantConfiguration result = null;
        for (PlantConfiguration plantConfiguration : plantConfigurations) {
            if (plantConfiguration.getClassName().equals(className)) {
                result = plantConfiguration;
                break;
            }
        }
        return result;
    }

    public AnimalConfiguration getAnimalConfigurationByName(String className) { //TODO Stream()
        AnimalConfiguration result = null;
        for (AnimalConfiguration animalConfiguration : animalConfigurations) {
            if (animalConfiguration.getClassName().equals(className)) {
                result = animalConfiguration;
                break;
            }
        }
        return result;
    }

    public <T extends FoodChainMember> int getEatChance(T hunter, T victim) {
        if (animalInteractionTable.isEmpty()) {
            createAnimalInteractionTable();
        }
        return animalInteractionTable.get(hunter.getClass().getSimpleName())
                .get(victim.getClass().getSimpleName());
    }

    private void createAnimalInteractionTable() {
        boolean headerCreated = false;
        List<String> animalsInOrder = new ArrayList<>();
        int counter = 0;
        for (List<String> paramsList : animalInteractionList) {
            if (!headerCreated) {
                for (String className : paramsList) {
                    if (animalsInOrder.size() != paramsList.size()) {
                        animalsInOrder.add(className);
                    }
                    animalInteractionTable.put(className, new HashMap<>());
                    for (String innerMapKey : paramsList) {
                        animalInteractionTable.get(className).put(innerMapKey, null);
                    }
                }
                headerCreated = true;
            } else {
                String className = animalsInOrder.get(counter);
                int paramCounter = 0;
                for (String animalInteraction : animalsInOrder) {
                    animalInteractionTable.get(className).put(animalInteraction, Integer.parseInt(paramsList.get(paramCounter++)));
                }
                counter++;
            }
        }
    }
}
