package com.mobiquityinc.packer;

import org.junit.Assert;
import org.junit.Test;

import com.mobiquityinc.helper.DataHelper;

public class PackerTest {

    @Test
    public void testPacker() throws Exception {
        String answers = DataHelper.loadTestAnswer("data/test_answers.txt");
        String result = Packer.pack(DataHelper.loadCases("data/test_cases.txt"));
        Assert.assertEquals(answers, result);
    }

    @Test
    public void testPackerOneCase() throws Exception {
        String result = Packer.pack(DataHelper.loadCases("data/test_case.txt"));
        Assert.assertEquals("1,2\n", result);
    }

    @Test
    public void testSpecPackerOneCase() throws Exception {
        String result = Packer.pack(DataHelper.loadOneCase("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)"));
        Assert.assertEquals("4\n", result);
    }

    @Test
    public void testSpecPackerOneCase2() throws Exception {
        String result = Packer.pack(DataHelper.loadOneCase("56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)"));
        Assert.assertEquals("8,9\n", result);

        // 8,9 - 19.36 + 6.76 = xxx, 79 + 64 = xxx   - b110000000 = 384
        // 6,9 - 48.77 + 6.76 = xxx, 79 + 64 = xxx   - b100100000 = 288

    }
}
