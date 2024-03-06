package com.mobiquityinc.algo;

import java.util.List;

import com.mobiquityinc.model.Case;

public class Algo {
    private static int i = 0;

    public static List<Integer> solve(Case aCase){
        if(i == 0){
            return DynamicProgramming.solve(aCase);
        } else {
            return Gridy.solve(aCase);
        }
    }
}
