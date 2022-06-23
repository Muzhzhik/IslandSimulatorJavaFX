package com.example.islandsimulatorjavafx.logic.domain.plant;

import com.example.islandsimulatorjavafx.logic.config.PlantConfiguration;
import com.example.islandsimulatorjavafx.logic.domain.FoodChainMember;
import lombok.Getter;

import java.util.Date;
import java.util.Random;

/**
 * @author Sergey Muzhzukhin
 * ¯\_(ツ)_/¯
 */
@Getter
public class Plant implements FoodChainMember {
    private final String name;
    private final int weight;
    private final int maxQuantityForCell;
    private final int lifeExpectancyLimit; // seconds
    private final int deathExpectancyLimit;
    private long createTime = new Date().getTime();
    private volatile boolean alive = true;

    private long deadTime = createTime;


    public Plant(PlantConfiguration configuration) {
        this.name = configuration.getName();
        this.weight = configuration.getWeight();
        this.maxQuantityForCell = configuration.getMaxQuantityForCell();
        int random = new Random().nextInt(configuration.getLifeExpectancyLimit());
        this.lifeExpectancyLimit = random == 0 ? 1 : random;
        random = new Random().nextInt(configuration.getDeathExpectancyLimit());
        this.deathExpectancyLimit = random == 0 ? 1 : random;
    }

    public void killOrRevive() {
        if (alive && plantMustDie()) {
            kill();
        } else if (!alive && plantMustRevive()) {
            alive = true;
            createTime = new Date().getTime();
        }
    }

    public void kill() {
        alive = false;
        deadTime = new Date().getTime();
    }

    private boolean plantMustDie() {
        long time = new Date().getTime() - createTime;
        return time > lifeExpectancyLimit * 1000L;
    }

    private boolean plantMustRevive() {
        long time = new Date().getTime() - deadTime;
        return time > deathExpectancyLimit * 1000L;
    }
}
