package com.testpack;

import com.dbdao.CompanyDBDAO;
import com.dbdao.CouponDBDAO;
import com.javabeans.Company;
import com.javabeans.Coupon;
import com.javabeans.CouponType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * This is a Test Class.
 * It's more pratical class. Is short, and check the Objects and the function in the fast way.
 * The other Test is just a beta version of test with scanners and User experience.
 *
 * I moved to work with the short Test because I don't think I will have enough time for Scanners and Visual in
 * this point. we just need the pratical thing. I WILL use the visual test and terminal or
 * maybe only the structure of it.
 *
 * @author Raziel
 *
 */

public class ShortTest {
    private static final Logger logger = LogManager.getLogger(ShortTest.class);
    public static void main(String[] args) throws SQLException {

        CouponDBDAO dbcoup = new CouponDBDAO();
        CompanyDBDAO dbcomp = new CompanyDBDAO();
        logger.info("dbcoup [{}]",dbcoup.getAllCoupon());
        logger.info("dbcomp.getAllCompanies() [{}]",dbcomp.getAllCompanies());

        Coupon c1 = new Coupon();
       logger.info("c1 [{}]",c1.toString());

        Coupon c3 = new Coupon();

        logger.info("c3.toString() [{}]",c3.toString());
        Coupon c4 = new Coupon();
        logger.info("c4.toString() [{}]",c4.toString());

        Company co = new Company();
        System.out.println("co.getCompName() = " + co.getCompName());
        logger.info("co.getCompName() [{}]",co.getCompName());

         CompanyDBDAO db = new CompanyDBDAO();

        logger.info("db.getAllCompanies() [{}]",db.getAllCompanies());

        System.out.println(db.getCoupons(2));
        logger.info("db.getCoupons [{}]",db.getCoupons(2));


    }

}
