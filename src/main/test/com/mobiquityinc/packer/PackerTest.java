package com.mobiquityinc.packer;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class PackerTest {

    @Test
    public void testPacker() throws Exception {
        String answers = loadTestAnswer();
        String result = Packer.pack("data/test_cases.txt");

        System.out.println(result);

        Assert.assertEquals(answers, result);
    }

    private String loadTestAnswer() throws FileNotFoundException {
        BufferedReader fis = new BufferedReader(new FileReader("data/test_answers.txt"));
        StringBuilder builder = new StringBuilder();
        fis.lines().forEach(line -> {
            builder.append(line).append("\n");
        });

        return builder.toString();
    }
}
