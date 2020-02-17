package com.mobiquityinc.helper;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;

public class DataHelperTest {

    @Test
    public void testPatternWeight100() {
        String line = "(5,100.00,€52)";

        Matcher matcher = DataHelper.THING_PATTERN.matcher(line);

        if (matcher.matches()) {
            Assert.assertEquals("5", matcher.group(1));
            Assert.assertEquals("100.00", matcher.group(2));
            Assert.assertEquals("52", matcher.group(3));
        } else {
            Assert.fail("pattern doesn't match");
        }
    }

    @Test
    public void testPatternZeros() {
        String line = "(0,0.00,€0)";

        Matcher matcher = DataHelper.THING_PATTERN.matcher(line);

        if (matcher.matches()) {
            Assert.assertEquals("0", matcher.group(1));
            Assert.assertEquals("0.00", matcher.group(2));
            Assert.assertEquals("0", matcher.group(3));
        } else {
            Assert.fail("pattern doesn't match");
        }
    }
}
