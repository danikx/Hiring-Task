package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.helper.DataHelper;
import com.mobiquityinc.model.Case;
import com.mobiquityinc.model.Thing;

import java.util.List;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        List<Case> cases = DataHelper.loadCases(filePath);

        final StringBuilder builder = new StringBuilder();

        for (Case aCase : cases) {
            if (aCase.getWeightLimit() == 0) {
                builder.append("-").append("\n");

            } else if (aCase.getThings().isEmpty()) {
                builder.append("-").append("\n");

            } else if (aCase.getThings().size() == 1) {
                final Thing thing = aCase.getThings().get(0);

                if (thing.getWeight() <= aCase.getWeightLimit()) {
                    builder.append(thing.getIndex()).append("\n");
                } else {
                    builder.append("-").append("\n");
                }
            } else {
                builder.append(solve(aCase)).append("\n");
            }
        }

        return builder.toString();
    }

    /**
     * Use Dynamic programming with tabulation to solve this problem.
     */
    private static String solve(Case aCase) {
        Thing[] weights = aCase.getThings().toArray(new Thing[0]);

        int[][] memo = new int[aCase.getThings().size() + 1][aCase.getWeightLimit() + 1];

        for (int i = 0; i <= aCase.getThings().size(); i++) {
            for (int j = 0; j <= aCase.getWeightLimit(); j++) {
                // fill up table

                if (i == 0 || j == 0) // if i or j is zero fill by zero
                    memo[i][j] = 0;

                    // if current weight is less than weight limit do work
                else if (weights[i - 1].getWeight() <= j) {
                    int idx = (int) Math.round(j - weights[i - 1].getWeight());

                    //choose the largest
                    memo[i][j] = Math.max(
                            weights[i - 1].getCost() + memo[i - 1][idx],
                            memo[i - 1][j]);
                } else {
                    // else just get previous value
                    memo[i][j] = memo[i - 1][j];
                }
            }
        }

        if (debug) {
            // print memo
            printMemo(aCase, memo);
            printMaxCost(memo, aCase);
        }

        StringBuilder builder = new StringBuilder();

        for (int i = aCase.getThings().size(), j = aCase.getWeightLimit(); i > 0; i--) {
            if (memo[i][j] != memo[i - 1][j]) {
                if (builder.length() > 0) builder.append(",");

                builder.append(i);

                j = (int) Math.round(j - weights[i - 1].getWeight());
            }
        }

        return builder.reverse().toString();
    }

    private static boolean debug = true;

    private static void printMaxCost(int[][] memo, Case aCase) {
        // print max cost
        int maxCost = memo[aCase.getThings().size()][aCase.getWeightLimit()];
        System.out.println("max cost: " + maxCost);
    }

    private static void printMemo(Case aCase, int[][] memo) {
        for (int i = 0; i <= aCase.getThings().size(); i++) {
            for (int j = 0; j <= aCase.getWeightLimit(); j++) {
                System.out.print(memo[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}