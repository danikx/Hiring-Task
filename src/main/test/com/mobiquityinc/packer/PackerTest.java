package com.mobiquityinc.packer;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackerTest {

    @Test
    public void testPattern() {
        String line = "(5,100.00,€52)";
        Pattern pattern = Pattern.compile("\\((\\d*),(\\d*.\\d*),.(\\d*)\\)");

        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            Assert.assertEquals("5", matcher.group(1));
            Assert.assertEquals("100.00", matcher.group(2));
            Assert.assertEquals("52", matcher.group(3));
        } else {
            Assert.fail("pattern doesn't match");
        }
    }
}
