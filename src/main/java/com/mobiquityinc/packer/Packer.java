package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Case;
import com.mobiquityinc.model.Thing;

import java.util.List;

public class Packer {
    private static final boolean debug = false;

    private Packer() {
    }

    public static String pack(List<Case> cases) throws APIException {
        final StringBuilder builder = new StringBuilder();

        for (Case aCase : cases) {
            if (aCase.getWeightLimit() == 0) { /* skip such case */
                builder.append("-").append("\n");

            } else if (aCase.getThings().isEmpty()) { /* if there is no things skip case */
                builder.append("-").append("\n");

            } else if (aCase.getThings().size() == 1) {
                /* if there is only one thing check if it suits the limitation */

                final Thing thing = aCase.getThings().get(0);

                if (thing.getWeight() <= aCase.getWeightLimit()) {
                    builder.append(thing.getIndex()).append("\n");
                } else {
                    builder.append("-").append("\n"); // if doesn't suit skip case
                }
            } else {
                builder.append(solve(aCase)).append("\n");
            }
        }

        return builder.toString();
    }

    private static String solve(Case aCase) {
        // transform list to array for convenience
        Thing[] weights = aCase.getThings().toArray(new Thing[0]);

        /* create a array where row represents available items, and column represent weight limit */
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

                    //choose the largest cost
                    memo[i][j] = Math.max(
                            weights[i - 1].getCost() + memo[i - 1][weightIdx], // the cost of plus the cost when i-1 things available
                            memo[i - 1][j]); // the cost when i-1 cost available
                } else {
                    // ok a thing weight is bigger than weight limit
                    // copy cost when i-1 things available
                    memo[i][j] = memo[i - 1][j];
                }
            }
        }

        if (debug) {
            printMemo(memo, aCase);
            printMaxCost(memo, aCase);
        }

        StringBuilder builder = new StringBuilder();

        // find selected things
        for (int i = aCase.getThings().size(), j = aCase.getWeightLimit(); i > 0; i--) {

            // check cost with previous row if they are different
            // it means that it includes the last thing
            if (memo[i][j] != memo[i - 1][j]) {
                if (builder.length() > 0) builder.append(",");

                builder.append(i);
                // subtract the weight of last item from j
                double left = j - weights[i - 1].getWeight();

                j = (int) Math.round(left);
            }
        }

        return builder.reverse().toString();
    }

    /**
     * prints max cost
     */
    private static void printMaxCost(int[][] memo, Case aCase) {
        // print max cost
        int maxCost = memo[aCase.getThings().size()][aCase.getWeightLimit()];
        System.out.println("max cost: " + maxCost);
        System.out.println();
    }

    /**
     * prints memo array
     */
    private static void printMemo(int[][] memo, Case aCase) {
        System.out.println("weight: "+aCase.getWeightLimit());

        for (int i = 0; i <= aCase.getThings().size(); i++) {
            for (int j = 0; j <= aCase.getWeightLimit(); j++) {
                System.out.print(memo[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}