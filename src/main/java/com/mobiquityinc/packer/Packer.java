package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.helper.DataHelper;
import com.mobiquityinc.model.Case;

import java.util.List;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        List<Case> result = DataHelper.loadCases(filePath);

        StringBuilder builder = new StringBuilder();

        for (Case aCase : result) {
            if (aCase.getMaxWeight() == 0) {
                builder.append("-").append("\n");
            } else if (aCase.getThings().isEmpty() || aCase.getThings().size() == 1) {
                builder.append("-").append("\n");
            } else {
                // analyze
                builder.append("?").append("\n");
            }
        }
        return builder.toString();
    }
}
