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

/** Data load helper class */
public class DataHelper {
    public static final Pattern THING_PATTERN = Pattern.compile("\\((\\d*),(\\d*.\\d*),.(\\d*)\\)");
    private static final String EXCEPTION_BAD_THING_FORMAT = "Bad thing format";
    private static final String EXCEPTION_NO_THING = "No things";
    private static final String EXCEPTION_BAD_CASE_FORMAT = "bad case format";

    /** Loads test cases from given file */
    public static List<Case> loadCases(String filePath) throws APIException {
        final List<Case> result = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                Case value = new Case();
                result.add(value);

                value.setWeightLimit(scanner.nextInt()); // get weight limit
                scanner.next(); //skip symbol ':'

                while (scanner.hasNext(THING_PATTERN)) {
                    value.addThing(parseThing(scanner.next()));
                }

                if (value.getThings().isEmpty()) {
                    throw new APIException(EXCEPTION_NO_THING);
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new APIException(e.getMessage());
        } catch (InputMismatchException e) {
            throw new APIException(EXCEPTION_BAD_CASE_FORMAT);
        }

        return result;
    }

    /** parse thing data from given string */
    private static Thing parseThing(String thing) throws APIException {
        final Matcher matcher = THING_PATTERN.matcher(thing);
        if (matcher.matches()) {
            try {
                return new Thing(
                        Integer.parseInt(matcher.group(1)),   // index
                        Double.parseDouble(matcher.group(2)), // weight
                        Integer.parseInt(matcher.group(3))  // cost
                );
            } catch (NumberFormatException e) {
                throw new APIException(EXCEPTION_BAD_THING_FORMAT + ": " + thing);
            }
        }

        throw new APIException(EXCEPTION_BAD_THING_FORMAT + ": " + thing);
    }
}
