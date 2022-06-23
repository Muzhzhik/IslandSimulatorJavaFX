package com.example.islandsimulatorjavafx.logic.domain.animal.herbivore;

import com.example.islandsimulatorjavafx.logic.config.AnimalConfiguration;
import com.example.islandsimulatorjavafx.logic.domain.animal.Animal;
import lombok.Getter;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
@Getter
public abstract class Herbivore extends Animal {
    protected Herbivore(AnimalConfiguration configuration) {
        super(configuration);
    }
}
