package com.mobiquityinc.algo;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.helper.PrintHelper;
import com.mobiquityinc.model.Case;
import com.mobiquityinc.model.Thing;

public class DynamicProgramming extends BaseBackpackResolver {
    private int weightLimit;
    private int thingsCount;
    private int[] costs;
    private int[] weights;
    private int[][] memo;
    private final int factor = 100;

    public List<Integer> packBackpack(Case aCase) {
        this.weightLimit = aCase.getWeightLimit();
        this.thingsCount = aCase.getThings().size();

        this.weights = new int[aCase.getThings().size() + 1];
        this.costs = new int[aCase.getThings().size() + 1];

        for (int i = 0; i < aCase.getThings().size(); i++) {
            this.weights[i + 1] = (int) (aCase.getThings().get(i).getWeight() * factor);
            this.costs[i + 1] = aCase.getThings().get(i).getCost();
        }

        this.memo = new int[aCase.getThings().size() + 1][weightLimit * factor + 1];

        for (int i = 1; i <= thingsCount; i++) { // go through all things
            for (int j = 1; j <= weightLimit * factor; j++) { // current weight - column
                if (weights[i] <= j) {
                    int weightIdx = j - weights[i];
                    // choose the largest cost
                    int a = costs[i] + memo[i - 1][weightIdx]; 
                    int b = memo[i - 1][weightIdx];

                    memo[i][j] = Math.max(a,b);

                    // memo[i][j] = Math.max(
                    //         costs[i] + memo[i - 1][weightIdx], // the cost if current item will be added to backpack
                    //                                            // plust previous max items cost
                    //         memo[i - 1][weightIdx] // cost of previous item
                    // );

                } else {
                    // ok a thing weight is bigger than weight limit just copy the previous cost
                    memo[i][j] = memo[i - 1][j];
                }
            }
        }

        System.out.println("");
        PrintHelper.printMemo(memo, aCase, factor);
        List<Integer> result = findItems();
        
        System.out.println("idx list: " + PrintHelper.arrayToStr(result));
        
        return result;

        // return solve(aCase);
    }

    public List<Integer> solve(Case aCase) {
        // transform list to array for convenience
        Thing[] weights = aCase.getThings().toArray(new Thing[0]);

        /*
         * create a array where row represents available items, and column represent
         * weight limit
         */
        int[][] memo = new int[aCase.getThings().size() + 1][aCase.getWeightLimit() + 1];

        // fill up memo
        for (int i = 0; i <= aCase.getThings().size(); i++) { // i represent things available to pack
            for (int j = 0; j <= aCase.getWeightLimit(); j++) { // j represent weight limit

                if (i == 0 || j == 0) // if i or j is zero fill with zero
                    memo[i][j] = 0;

                else if (weights[i - 1].getWeight() <= j) { // check if a thing weight is less than weight limit do work

                    // lets count how much weight is left
                    double leftWeight = j - weights[i - 1].getWeight();
                    // our weight is double cast it to int
                    int weightIdx = (int) Math.round(leftWeight);

                    // choose the largest cost
                    memo[i][j] = Math.max(
                            weights[i - 1].getCost() + memo[i - 1][weightIdx], // the cost of plus the cost when i-1
                                                                               // things available
                            memo[i - 1][j] // the cost when i-1 cost available
                    );
                } else {
                    // ok a thing weight is bigger than weight limit
                    // copy cost when i-1 things available
                    memo[i][j] = memo[i - 1][j];
                }
            }
        }

        if (isDebug()) {
            PrintHelper.printMemo(memo, aCase);
            PrintHelper.printMaxCost(memo, aCase);
        }

        // find selected things
        return findSelectedThings(memo, aCase.getWeightLimit(), aCase.getThings().size(), weights);
    }

    private List<Integer> findItems(){
        List<Integer> idx = new ArrayList<>();
 
        for (int i = thingsCount, j = weightLimit * factor; i > 0; i--) {
            if (memo[i][j] != memo[i - 1][j]) {  
                idx.add(i);
                j = j - weights[i];
            }
        }

        return idx;
    }

    private List<Integer> findSelectedThings2() {
        List<Integer> idx = new ArrayList<>();

        int lastInd = thingsCount;
        for (int i = thingsCount, j = weightLimit * factor; i > 0; i--) {
            if(memo[i][j] != memo[i-1][j]){
                int ci = i;
                
                for(int k = i+1; k < lastInd; k++ ){
                    if(weights[k] < weights[i]){
                        ci = k;
                    }
                }
                
                idx.add(ci);
                lastInd = ci;
                j = j - weights[i];
            }
        }

        return idx;
    }

    private List<Integer> findSelectedThings(int[][] memo, int weightLimit, int thingsCount, Thing[] weights) {
        List<Integer> list = new ArrayList<>();

        for (int i = thingsCount, j = weightLimit; i > 0 && j > 0; i--) {
            if (memo[i][j] != memo[i - 1][j]) {
                list.add(i);
            } else {
                // so we got situation where we have equals cost but we ensure that we choose
                // minimum weight.
                int index = i;
                double minWeight = weights[i - 1].getWeight();
                int k = i;

                while (memo[i][j] == memo[k - 1][j]) {
                    if (minWeight > weights[k - 2].getWeight()) {
                        index = k;
                        minWeight = weights[k - 2].getWeight();
                    }
                    k--;
                }

                list.add(index);
                i = k;
            }

            // subtract the weight of last item from j
            double left = j - weights[i - 1].getWeight();

            j = (int) Math.round(left);
        }

        return list;
    }

    private String solve2(Case aCase) {
        // transform list to array for convenience
        Thing[] weights = aCase.getThings().toArray(new Thing[0]);

        /*
         * create a array where row represents available items, and column represent
         * weight limit
         */
        int[][] memo = new int[aCase.getThings().size() + 1][aCase.getWeightLimit() + 1];

        // fill up memo
        for (int i = 0; i <= aCase.getThings().size(); i++) { // i represent things available to pack
            for (int j = 0; j <= aCase.getWeightLimit(); j++) { // j represent weight limit

                if (i == 0 || j == 0) // if i or j is zero fill with zero
                    memo[i][j] = 0;

                else if (weights[i - 1].getWeight() <= j) { // check if a thing weight is less than weight limit do work

                    // lets count how much weight is left
                    double leftWeight = j - weights[i - 1].getWeight();
                    // our weight is double cast it to int
                    int weightIdx = (int) Math.round(leftWeight);

                    // choose the largest cost
                    memo[i][j] = Math.max(
                            weights[i - 1].getCost() + memo[i - 1][weightIdx], // the cost of plus the cost when i-1
                                                                               // things available
                            memo[i - 1][j]); // the cost when i-1 cost available
                } else {
                    // ok a thing weight is bigger than weight limit
                    // copy cost when i-1 things available
                    memo[i][j] = memo[i - 1][j];
                }
            }
        }

        if (isDebug()) {
            PrintHelper.printMemo(memo, aCase);
            PrintHelper.printMaxCost(memo, aCase);
        }

        StringBuilder builder = new StringBuilder();

        // find selected things
        for (int i = aCase.getThings().size(), j = aCase.getWeightLimit(); i > 0; i--) {

            // check cost with previous row if they are different
            // it means that it includes the last thing
            if (memo[i][j] != memo[i - 1][j]) {
                if (builder.length() > 0)
                    builder.append(",");

                builder.append(i);
                // subtract the weight of last item from j
                double left = j - weights[i - 1].getWeight();

                j = (int) Math.round(left);
            }
        }

        return builder.reverse().toString();
    }
}
