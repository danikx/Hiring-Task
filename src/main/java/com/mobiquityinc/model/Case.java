package com.mobiquityinc.model;

import java.util.ArrayList;
import java.util.List;

public class Case {
    private int maxWeight;
    private List<Thing> things = new ArrayList<>();

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<Thing> getThings() {
        return things;
    }

    public void setThings(List<Thing> things) {
        this.things = things;
    }

    public void addThings(Thing thing) {
        this.things.add(thing);
    }
}
