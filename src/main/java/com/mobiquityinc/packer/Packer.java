package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.helper.PrintHelper;
import com.mobiquityinc.model.Case;
import com.mobiquityinc.model.Thing;
import com.mobiquityinc.algo.*;

import java.util.List;


public class Packer {

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

                Thing thing = aCase.getThings().get(0);

                if (thing.getWeight() <= aCase.getWeightLimit()) {
                    builder.append(thing.getIndex()).append("\n");
                } else {
                    builder.append("-").append("\n"); // if doesn't suit skip case
                }
            } else {
                builder.append(PrintHelper.arrayToStr(Algo.solve(aCase))).append("\n");
            }
        }

        return builder.toString();
    }
}