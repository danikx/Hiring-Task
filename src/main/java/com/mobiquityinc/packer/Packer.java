package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Case;
import com.mobiquityinc.model.Thing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Packer {
    private static Pattern pattern = Pattern.compile("\\((\\d*),(\\d*.\\d*),.(\\d*)\\)");

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {
        List<Case> result = loadData(filePath);

        return null;
    }

    private static List<Case> loadData(String filePath) throws APIException {
        List<Case> result = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                Case value = new Case();
                result.add(value);

                value.setWeight(scanner.nextInt());
                scanner.next(); //skip :

                while (scanner.hasNext("\\(.*\\)")) {
                    value.addThings(splitLine(scanner.next()));
                }
            }
        } catch (IOException e) {
            throw new APIException(e.getMessage());
        }

        return result;
    }


    private static Thing splitLine(String line) throws APIException {
        System.out.println(line);

        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return new Thing(
                    Integer.parseInt(matcher.group(1)),
                    Double.parseDouble(matcher.group(2)),
                    Double.parseDouble(matcher.group(3))
            );
        }

        throw new APIException("bad format ");
    }

    public static void main(String[] args) throws APIException {
        String result = Packer.pack("data/test_cases.txt");
        System.out.println(result);
    }
}
