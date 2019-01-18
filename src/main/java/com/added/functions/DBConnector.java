package com.added.functions;

import java.sql.*;

public class DBConnector {
	
	/**
	 * @author Raziel
	 * This is a Helper Class - Database Connector. 
	 * We using it all over the project classes to set connection
	 * or getting the current of the connection (it will be 'getInsance()' ).
	 */
	
	protected static Connection con;
	protected static final String url = "jdbc:mysql://localhost/coupon?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	protected static final String userDBname = "root";
	protected static final String passowrdDB = "";

	public DBConnector() {}
	
	public static Connection getInstance()
	{
		return con;
	}
	
	public static Connection getCon() {
		
		 try {
			 System.out.println("Connecting to db:");
		 	con = DriverManager.getConnection(url, userDBname, passowrdDB);
			 System.out.println("Connected to db:" + con);
		}
		catch (SQLException e) {
			 System.out.println("Can't connect db: ");
			 e.printStackTrace();
		}
		 return con;
	}
	
}
