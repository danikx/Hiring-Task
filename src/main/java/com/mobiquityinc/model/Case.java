package com.mobiquityinc.model;

import java.util.ArrayList;
import java.util.List;

/** Data class for test case */
public class Case {
    private int weightLimit;
    private List<Thing> things = new ArrayList<>();

    public int getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(int maxWeight) {
        this.weightLimit = maxWeight;
    }

    public List<Thing> getThings() {
        return things;
    }

    public void setThings(List<Thing> things) {
        this.things = things;
    }

    public void addThing(Thing thing) {
        this.things.add(thing);
    }
}
