package com.example.islandsimulatorjavafx.logic.domain.animal.predator;

import com.example.islandsimulatorjavafx.logic.config.AnimalConfiguration;
import com.example.islandsimulatorjavafx.logic.domain.animal.Animal;
import lombok.Getter;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
@Getter
public abstract class Predator extends Animal {
    protected Predator(AnimalConfiguration configuration) {
        super(configuration);
    }
}
