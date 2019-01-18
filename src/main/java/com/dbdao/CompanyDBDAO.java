package com.dbdao;

import java.sql.*;
import java.util.Collection;
import java.util.*;

import com.added.functions.DBConnector;
import com.added.functions.IsExistDB;
import com.added.functions.SharingData;
import com.dao.interfaces.*;
import com.javabeans.*;

/**
 * This is Company Database DAO Class.
 * Just impelemnts the methods from CompanyDAO in 'com.dao.intefaces' package. 
 * @author Raziel
 *
 */

public class CompanyDBDAO implements CompanyDAO {
	
	// Default Constructor.
	public CompanyDBDAO() {}
	
	@Override
	public void createCompany(Company company) {
		
		// creating ResultSet
		ResultSet rs = null;
		
		try {
			DBConnector.getCon();
			String sqlQuery = "INSERT INTO coupon.company (COMP_NAME, PASSWORD, EMAIL) VALUES(?,?,?)";
			PreparedStatement prep = DBConnector.getInstance().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			//short cou = 1;
			
			// now we will put the in their places.
			prep.setString(1, company.getCompName());
			prep.setString(2, company.getPassword());
			prep.setString(3, company.getEmail());
		
			// after we "loaded" the columns, we will executeUpdate prep.
			prep.executeUpdate();

			// This 2 lines will make it run to the next null row. and line 3 will set the ID (next new row).
			rs = prep.getGeneratedKeys();
			rs.next();
			company.setId(rs.getLong(1));
			
			// Letting the others (if the asking) that the Company Added Succsefully.
			SharingData.setFlag1(true);
			String tostring = company.toString();
			SharingData.setVarchar4(tostring);

			
		} // try 
		catch (SQLException e) {
			SharingData.setFlag1(false);
			e.printStackTrace(); // TODO: by the project guide, WE DON'T NEED TO PRINT the StackTrace.
		} // catch
		finally {
			try {
				DBConnector.getInstance().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		
	} // createCompany - Function

	// 3 Remove methods! 
	
	//1. By Company.
	@Override
	public void removeCompany(Company company) throws SQLException {
		
		IsExistDB.idExist(company.getId());
		if(IsExistDB.getAnswer2() == true) {

			//ResultSet rs = null;
			PreparedStatement prep = null;
			
			try {
				
				DBConnector.getCon();
				String sqlDELobject = "DELETE FROM company WHERE Comp_ID =?";
				prep = DBConnector.getInstance().prepareStatement(sqlDELobject);
				prep.setLong(1, company.getId());
				prep.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
			
			finally {
				DBConnector.getInstance().close();
			} // finally
		}

		
	}
	
	//2. By ID (long)
	@Override
	public void removeCompany(long id) throws SQLException{
		// test
		
		try {
			DBConnector.getCon();
			//String compName, email, password;
			
			String sqlDELid = "DELETE FROM coupon.company WHERE Comp_ID =?" ;
			PreparedStatement prep = DBConnector.getInstance().prepareStatement(sqlDELid);
			prep.setLong(1, id);
			prep.executeUpdate();
			
		}
		catch (SQLException e) {
			e.getMessage();
		}
		finally {
			DBConnector.getInstance().close();
		} // finally
		
	} // removeCompany - By ID - Function
	
	//.3 By Name (String)
	@Override
	public void removeCompany(String name) throws SQLException {
		
		try {
			DBConnector.getCon();
			String sqlDELname = "DELETE FROM coupon.company WHERE Comp_name =?" ;
			PreparedStatement prep = DBConnector.getInstance().prepareStatement(sqlDELname);
			prep.setString(1, name);
			
			prep.executeUpdate();
			
			// Letting the other Classes (if they asking) that the Company Removed Succsefully.
			SharingData.setFlag1(true);
		}
		catch (SQLException e) {
			e.getStackTrace();
		}
		finally {
			DBConnector.getInstance().close();
		} // finally
		
	} // removeCompany - BY Name - Function
	
	
	@Override
	public void updateCompany(Company company) {

		try {
			
			DBConnector.getCon();
			
			String sqlCmdStr = "UPDATE coupon.company SET Comp_name=?, password=?, email=? WHERE Comp_ID=?";
			PreparedStatement prep = DBConnector.getInstance().prepareStatement (sqlCmdStr);
			prep.setString(1, company.getCompName());
			prep.setString(2, company.getPassword());
			prep.setString(3, company.getEmail());
			prep.setLong(4, company.getId());
			
			prep.executeUpdate();
			
		    // Letting the others (if the asking) that the Company update Succsefully.
		    SharingData.setFlag1(true);
		    String tostring = company.toString();
			SharingData.setVarchar4(tostring);

			} catch (SQLException e) {
			e.printStackTrace();
			}
			finally {
			try {
				
			DBConnector.getInstance().close();
			} catch (SQLException e) {
			e.printStackTrace();
					} // catch
			} // finally
				

		
	} // updateCompany - Function

	
	@Override
	public Company getCompany(long id) {
		
		Company c = null;
		String compName, email, password;
		
		try {

			DBConnector.getCon();
			String sqlSEL = "SELECT * FROM coupon.company WHERE Comp_ID= ?" ;
			PreparedStatement prep = DBConnector.getInstance().prepareStatement(sqlSEL);
			prep.setLong(1, id);
			
			ResultSet rs = prep.executeQuery();
			rs.next();
			compName = rs.getString("Comp_name");
			email = rs.getString("Email");
			password = rs.getString("password");
			

			c = new Company(id, compName, password, email);
			String companyInfo = c.toString();
			SharingData.setVarchar2(companyInfo);

			// Letting the other Classes (if they asking) that the getID Function was run Succsefully.
			SharingData.setFlag1(true);
			
		}
		catch (SQLException e) {
			e.getStackTrace();
		}
		
		finally {
			try {
				DBConnector.getInstance().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		return c;
		
		
	} // getCompany - Function

	@Override
	public Collection<Company> getAllCompanies() {
		
		String sql = "SELECT * FROM company";
		Collection<Company> companies = new HashSet<>();
		Company c = null;
		ResultSet rs = null;
		
		try {
			DBConnector.getCon();
			Statement stat = DBConnector.getInstance().createStatement();
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				c = new Company();
				c.setId(rs.getLong("Comp_ID"));
				c.setCompName(rs.getString("Comp_name"));
				c.setPassword(rs.getString("password"));
				c.setEmail(rs.getString("email"));
				
				companies.add(c);
			} // while loop

		} catch (SQLException e) {
			e.printStackTrace();
		} // catch
		
		finally {
			try {
				rs.close();
				DBConnector.getInstance().close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
		} // finally
		
		
		return companies;
	} // getAllCompanies

	@Override
	public Collection<Coupon> getCoupons(long compID) {
		 
		// We can test it in some simple tests (like ShortTest Class). NOT in the testDevelopers Class.
		
		Set<Coupon> coupons = new HashSet<>();
		CouponDBDAO  couponDB = new CouponDBDAO();
		
		try {
			DBConnector.getCon();
			String sql = "SELECT Coup_ID FROM Company_Coupon WHERE Comp_ID=?";
			PreparedStatement stat = DBConnector.getInstance().prepareStatement (sql);
			stat.setLong(1, compID);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				coupons.add(couponDB.getCoupon(rs.getLong("Coup_ID")));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coupons;
	}

	@Override
	public boolean login(String compName, String password) {
		
		ResultSet rs1 = null;
		Statement stat1 = null;

		boolean hasRows = false;
try {
			
			DBConnector.getCon();
			String sqlName = "SELECT Comp_name, password FROM company WHERE "
					+ "Comp_name= '" + compName + "'" + " AND " + "password= '" 
					+ password + "'";
			stat1 = DBConnector.getInstance().createStatement();
		    rs1 = stat1.executeQuery(sqlName);
		    rs1.next();

			if (rs1.getRow() != 0) {
				hasRows = true;
				
				System.out.println(hasRows);
			}

            } catch (SQLException e) {
	        e.printStackTrace();
	        
            } // catch
		finally {
			try {
				DBConnector.getInstance().close();
			} catch (SQLException e) {
				
			}
		}// finally

	return hasRows;
	}	
	

}
