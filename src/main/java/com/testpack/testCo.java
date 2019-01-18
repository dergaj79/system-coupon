package com.testpack;

import java.sql.SQLException;

import com.added.functions.*;
import com.javabeans.*;
import org.testng.annotations.Test;

public class testCo {

    //TODO: write here a nice description..

    /**
     * @author Raziel
     * This test class is only for training and checking the added Class/Methods: DataRowGetter
     * ONLY FOR THE DEVELOPERS.
     * @throws SQLException
     *
     */

    public static void main(String[] args) throws SQLException {

        System.out.println("args = " + args);

    }

    @Test(testName = " test method for company create")
    public  void  testMyMethod() throws SQLException {

        Company c = new Company();
        c.setCompName("asus");
        c.setEmail("aceasfafgafr@gmail.com");
        c.setPassword("1234");
        c.setId(456634);

        DataRowGetter.getCompanyRow(c);
        if(DataRowGetter.isVerificationExistInDataB() == true) {
            System.out.println(c.toString() + "\n");
        }
        else {
            testDeveloers.printNoExistOrCurrect();
            System.out.println("Pay Attention, Type ONLY ACCORDING to the instructions Below: "
                    + "\n" + "1. UserName, Password."
                    + "\n" + "2. UserName, Password, Email. "
                    + "\n" + "3. UserName, Password, ID. "
                    + "\n" + "4. UserName, Password, ID, Email. ");

            System.out.println("You can be Wrong about the ID or Email.. the program will fixed it for you.");
            System.out.println("But you CAN'T be WRONG about the UserName and Password." + "\n");

        }

        System.out.println("Number: " + DataRowGetter.getCountANDcalculate());
        System.out.println("IsExist: " + IsExistDB.getAnswer());
        System.out.println("DataRowGetter: " + DataRowGetter.isVerificationExistInDataB());
        System.out.println("Verify Nubmer: " + DataRowGetter.getVerifyByNumber());


    }

}