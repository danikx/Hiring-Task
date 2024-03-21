package com.mobiquityinc.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.model.Case;
import com.mobiquityinc.model.Thing;

public class BruteForce extends BaseBackpackResolver{
    private int maxValue = 0;
    private double maxTotalWeight = Double.MAX_VALUE;
    private List<Thing> maxKnapsack = new ArrayList<>();

    @Override
    public List<Integer> packBackpack(Case aCase){
        clear();

        int n = aCase.getThings().size();
        int capacity = aCase.getWeightLimit();
        int count = 1 << n; // bitmask

        for(int i = 0; i < count; i++){
            List<Thing> knapsack = new ArrayList<>();
            double totalWeight = 0.;
            int totalValue = 0;

            for(int j = 0; j < n; j++ ){  
                if( (( i >> j) & 1) == 1 ){
                    knapsack.add(aCase.getThings().get(j));
                    totalWeight += aCase.getThings().get(j).getWeight();
                    totalValue  += aCase.getThings().get(j).getCost();
                }
            }

            if(knapsack.size() > 0){
                if (totalWeight <= capacity) {
                    if(totalValue > maxValue){
                       updateMaxValues(totalValue, knapsack, totalWeight);
                    } else if(totalValue == maxValue && totalWeight < maxTotalWeight){
                        updateMaxValues(totalValue, knapsack, totalWeight);
                    }
                }
            }
        }

        return maxKnapsack.stream().map(think -> think.getIndex()).collect(Collectors.toList());
    }

    protected void updateMaxValues(int totalValue, List<Thing> knapsack, double totalWeight){
        this.maxValue = totalValue;
        maxKnapsack = knapsack;
        maxTotalWeight = totalWeight;
    }

    protected void clear(){
        maxValue = 0;
        maxTotalWeight = Double.MAX_VALUE;
        maxKnapsack = new ArrayList<>();
    }
}