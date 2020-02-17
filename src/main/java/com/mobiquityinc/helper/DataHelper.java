package com.mobiquityinc.helper;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Case;
import com.mobiquityinc.model.Thing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHelper {
    public static final Pattern THING_PATTERN = Pattern.compile("\\((\\d*),(\\d*.\\d*),.(\\d*)\\)");
    private static final String EXCEPTION_BAD_THING_FORMAT = "Bad thing format";


    public static List<Case> loadCases(String filePath) throws APIException {
        List<Case> result = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter(" ");

            while (scanner.hasNext()) {
                Case value = new Case();
                result.add(value);

                value.setMaxWeight(scanner.nextInt());
                scanner.next(); //skip symbol ':'

                while (scanner.hasNext(THING_PATTERN)) {
                    value.addThings(parseThing(scanner.next()));
                }
            }
        } catch (IOException | InputMismatchException e) {
            throw new APIException(e.getMessage());
        }

        return result;
    }

    public static Thing parseThing(String thing) throws APIException {
        Matcher matcher = THING_PATTERN.matcher(thing);
        if (matcher.matches()) {
            try {
                return new Thing(
                        Integer.parseInt(matcher.group(1)),   // index
                        Double.parseDouble(matcher.group(2)), // weight
                        Double.parseDouble(matcher.group(3))  // cost
                );
            } catch (NumberFormatException e) {
                throw new APIException(EXCEPTION_BAD_THING_FORMAT + ": " + thing);
            }
        }

        throw new APIException(EXCEPTION_BAD_THING_FORMAT + ": " + thing);
    }
}
