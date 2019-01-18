package com.testpack;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test()
public class Testng {


    @DataProvider
    public Object[][] data() {
        return new String[][] {new String[] {"data1"}, new String[] {"data2"}};
    }

    @Test(dataProvider = "data")
    public void test(String d) {
        Assert.assertEquals("First Line>\nSecond Line", "Third Line\nFourth Line");
    }
}