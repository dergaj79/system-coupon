package com.added.functions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Test;

import java.sql.Connection;


public class DBConnectorTest {
    private static final Logger logger = LogManager.getLogger(DBConnectorTest.class);

    private static Connection con;
    private static final String url = "jdbc:mysql://localhost/coupon?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String userDBname = "root";
    private static final String passowrdDB = "";


    @Test (testName = "this test will test test get test connection to db")
    public void testGetCon() {

        DBConnector dbConnector = new DBConnector();

        System.out.println(dbConnector);
        DBConnector.getCon();
        System.out.println("dbConnector. = " + DBConnector.getInstance());
        System.out.println("dbConnector2 = " + DBConnector.getInstance());
        logger.info("this will stat method  [{}]", DBConnector.getInstance());
        logger.info("this will stat method  [{}]", DBConnector.getInstance());
        logger.info("this will stat method  [{}]", dbConnector);
    }
}