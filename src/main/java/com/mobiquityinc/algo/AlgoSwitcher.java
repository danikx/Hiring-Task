package com.mobiquityinc.algo;

import java.util.List;

import com.mobiquityinc.model.Case;

public class AlgoSwitcher {
    /**
     * Only BruteForce is working solution.
     * We can't use DynamicProgramming when weight is real number.
     * Gridy algorithm is also not effective solution.
     */
    private static final BaseBackpackResolver dp = new DynamicProgramming();
    private static final BaseBackpackResolver bf = new BruteForce();
    private static int i = 1;

    public static List<Integer> solve(Case aCase){
        if(i == 0) {
            return dp.packBackpack(aCase);
        } else if (i == 1) {
            return bf.packBackpack(aCase);
        } else {
            return Gridy.solve(aCase);
        }
    }
}
