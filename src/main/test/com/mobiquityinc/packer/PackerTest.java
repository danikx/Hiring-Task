package com.mobiquityinc.packer;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class PackerTest {

    @Test
    public void testPacker() throws Exception {
        String answers = loadTestAnswer("data/test_answers.txt");
        String result = Packer.pack("data/test_cases.txt");
        Assert.assertEquals(answers, result);
    }

    @Test
    public void testPacker1() throws Exception {
        String result = Packer.pack("data/test_cases_.txt");
        Assert.assertEquals("1,2\n", result);
    }

    private String loadTestAnswer(String filePath) throws FileNotFoundException {
        BufferedReader fis = new BufferedReader(new FileReader(filePath));
        StringBuilder builder = new StringBuilder();
        fis.lines().forEach(line -> {
            builder.append(line).append("\n");
        });

        return builder.toString();
    }
}
