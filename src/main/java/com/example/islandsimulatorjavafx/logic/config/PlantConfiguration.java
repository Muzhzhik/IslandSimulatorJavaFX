package com.example.islandsimulatorjavafx.logic.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
@NoArgsConstructor
@Getter
@Setter
public class PlantConfiguration {
    private String className;
    private String name;
    private int weight;
    private int maxQuantityForCell;
    private int lifeExpectancyLimit; // seconds
    private int deathExpectancyLimit;
}
