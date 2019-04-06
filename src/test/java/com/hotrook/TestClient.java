package com.hotrook;

import org.junit.Assert;
import org.junit.Test;

public class TestClient {

    @Test
    public void testParseData() {
        String testString = "set active;1000;A";
        String[] result = Client.parseData(testString);
        Assert.assertEquals("set active", result[0]);
        Assert.assertEquals("1000", result[1]);
        Assert.assertEquals("A", result[2]);
        Assert.assertEquals(3, result.length);
    }
}
