package com.dbdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import com.added.functions.DBConnector;
import com.added.functions.SharingData;
import com.dao.interfaces.CustomerDAO;
import com.javabeans.Coupon;
import com.javabeans.Customer;

/**
 * This is Customer Database DAO Class.
 * Just impelemnts the methods from CustomerDAO in 'com.dao.intefaces' package. 
 * @author Raziel
 *
 */

public class CustomerDBDAO implements CustomerDAO {

	@Override
	public void createCustomer(Customer newCustomer) {
		
		// creating ResultSet
				ResultSet rs = null;
				long id = 1;
				try {
					DBConnector.getCon();
					String sqlQuery = "INSERT INTO customer (CUST_NAME, PASSWORD) VALUES(?,?)";
					PreparedStatement prep = DBConnector.getInstance().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
					//short cou = 1;
					
					// now we will put the in their places.
					prep.setString(1, newCustomer.getCustName());
					prep.setString(2, newCustomer.getPassword());
				
					// after we "loaded" the columns, we will executeUpdate prep.
					prep.executeUpdate();

					// This 2 lines will make it run to the next null row. and line 3 will set the ID (next new row).
					rs = prep.getGeneratedKeys();
					rs.next();
					id = rs.getLong(1);
					newCustomer.setId(id);
					
					// Letting the others (if the asking) that the Company Added Succsefully.
					SharingData.setFlag1(true);
					String tostring = newCustomer.toString();
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
	

	public CustomerDBDAO() {}

	@Override
	public void removeCustomer(Customer remCustomer) {
		//TODO: create remove by object Customer.
		
	}
	
	public void removeCustomer(long id) throws SQLException{
		// test
		
		try {
			DBConnector.getCon();
			//String compName, email, password;
			
			String sqlDELid = "DELETE FROM customer WHERE Cust_id =?" ;
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
		
	} // removeCompany - By ID - Functi

	public void removeCustomer(String name) throws SQLException {
		
		try {
			DBConnector.getCon();
			String sqlDELname = "DELETE FROM Customer WHERE cust_name =?" ;
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
	public void updateCustomer(Customer updateCustomer) {
       try {
			
			DBConnector.getCon();
			
			String sqlCmdStr = "UPDATE customer SET Cust_name=?, password=? WHERE Cust_ID=?";
			PreparedStatement prep = DBConnector.getInstance().prepareStatement (sqlCmdStr);
			prep.setString(1, updateCustomer.getCustName());
			prep.setString(2, updateCustomer.getPassword());
			prep.setLong(3, updateCustomer.getId());
			
			prep.executeUpdate();
			
		    // Letting the others (if the asking) that the Company update Succsefully.
		    SharingData.setFlag1(true);
		    String tostring = updateCustomer.toString();
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
		
		
	}

	@Override
	public Customer getCustomer(long id) {
		

		Customer c = null;
		String custName, password;
		
		try {

			DBConnector.getCon();
			String sqlSEL = "SELECT * FROM customer WHERE Cust_ID= ?" ;
			PreparedStatement prep = DBConnector.getInstance().prepareStatement(sqlSEL);
			prep.setLong(1, id);
			
			ResultSet rs = prep.executeQuery();
			rs.next();
			custName = rs.getString("Cust_name");
			//email = rs.getString("Email");
			password = rs.getString("password");
			

			c = new Customer(id, custName, password);
			String customerInfo = c.toString();
			SharingData.setVarchar2(customerInfo);

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
	}

	@Override
    public Collection<Customer> getAllCustomers() {
		
		String sql = "SELECT * FROM customer";
		Collection<Customer> customers = new HashSet<>();
		Customer c = null;
		ResultSet rs = null;
		
		try {
			DBConnector.getCon();
			Statement stat = DBConnector.getInstance().createStatement();
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				c = new Customer();
				c.setId(rs.getLong("Cust_ID"));
				c.setCustName(rs.getString("Cust_name"));
				c.setPassword(rs.getString("password"));
				//c.setEmail(rs.getString("email"));
				
				customers.add(c);
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
		
		
		return customers;
	} // getAllCompanies

	@Override
	public Collection<Coupon> getCoupons() {
		// TODO: working on this function
		try {

			DBConnector.getCon();
			Collection<Coupon> coupons = new HashSet<>();
			
			String sql = "SELECT coup_id FROM customer_coupon WHERE cust_id=?";
			PreparedStatement prep = DBConnector.getInstance().prepareStatement(sql);
			//prep.setLong(1, cust_id);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public boolean login(Customer custName, Customer password) {
		
		return false;
	}


	

}
