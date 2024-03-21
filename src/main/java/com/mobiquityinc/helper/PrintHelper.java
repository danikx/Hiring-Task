package com.mobiquityinc.helper;

import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.mobiquityinc.model.Case;

public class PrintHelper {

    /**
     * prints max cost
     */
    public static void printMaxCost(int[][] memo, Case aCase) {
        // print max cost
        int maxCost = memo[aCase.getThings().size()][aCase.getWeightLimit()];
        System.out.println("Max cost: " + maxCost);
        System.out.println();
    }

    /**
     * prints memo array
     */
    public static void printMemo(int[][] memo, Case aCase) {
        System.out.println("Weight: " + aCase.getWeightLimit());

        // print header
        System.out.print("    ");

        for (int i = 0; i <= aCase.getWeightLimit(); i++) {
            System.out.print(String.format("%3d", i) + ' ');
        }

        System.out.println();

        for (int i = 0; i <= aCase.getThings().size(); i++) {
            System.out.print(String.format("%2d: ", i));

            for (int j = 0; j <= aCase.getWeightLimit(); j++) {
                System.out.print(String.format("%3d", memo[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printMemo(int[][] memo, Case aCase, int factor) {
        System.out.println("Weight: " + aCase.getWeightLimit() * factor);

        // print header
        System.out.print("    ");

        for (int i = 0; i <= aCase.getWeightLimit() * factor; i++) {
            System.out.print(String.format("%3d", i) + ' ');
        }

        System.out.println();

        for (int i = 0; i <= aCase.getThings().size(); i++) {
            System.out.print(String.format("%2d: ", i));

            for (int j = 0; j <= aCase.getWeightLimit() * factor; j++) {
                System.out.print(String.format("%3d", memo[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static String arrayToStr(java.util.List<Integer> indexes) {
        return indexes.stream()
                // .sorted()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    // private static String arrayToStr2(java.util.List<Integer> indexes) {
    //     StringJoiner builder = new StringJoiner(",");

    //     for (Integer v : indexes) {
    //         builder.add(v.toString());
    //     }

    //     return builder.toString();
    // }
}
