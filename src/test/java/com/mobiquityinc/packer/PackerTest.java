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
}
