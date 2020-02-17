package com.mobiquityinc.model;

import java.util.ArrayList;
import java.util.List;

public class Case {
    private int weight;
    private List<Thing> things = new ArrayList<>();

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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
