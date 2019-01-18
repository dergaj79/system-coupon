package com.testng.hsenid;

import com.added.functions.DBConnectorTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;
import org.testng.annotations.Test;



public class MyConcatenatorTest {
    private static final Logger logger = LogManager.getLogger(DBConnectorTest.class);
    @Test (testName = "This is test will check connection to db")
    public void testConcatanate() throws Exception {
        String concatenated = MyConcatenator.concatanate("one", "two", "three", "four");
        logger.info("concatenated [{}]", concatenated);
        logger.info("concatenated [{}]", concatenated);
        logger.info("concatenated [{}]", concatenated);
        logger.info("concatenated [{}]", concatenated);
        logger.info("concatenated [{}]", concatenated);

        Assert.assertEquals("one,two,three,four", concatenated);

    }
}