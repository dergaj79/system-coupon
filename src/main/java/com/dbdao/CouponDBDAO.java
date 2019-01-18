package com.dbdao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.added.functions.DBConnector;
import com.added.functions.IsExistDB;
import com.added.functions.SharingData;
import com.dao.interfaces.CouponDAO;
import com.javabeans.Coupon;
import com.javabeans.CouponType;

/**
 * This is Coupon Database DAO Class.
 * Just impelemnts the methods from CouponDAO in 'com.dao.intefaces' package. 
 * @author Raziel
 *
 */

public class CouponDBDAO implements CouponDAO{

	@Override
	public long createCoupon(Coupon coupon) {
		
		long id = -1;
		
		// creating ResultSet
		ResultSet rs = null;
		
		try {
			
			DBConnector.getCon();
			String sqlQuery = "INSERT INTO coupon (Title, Start_Date, End_Date, " + 
			"Amount, Category, Message, Price, Image)" + "VALUES(?,?,?,?,?,?,?,?)";	
			PreparedStatement prep = DBConnector.getInstance().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, coupon.getTitle());
			prep.setDate(2, Date.valueOf(coupon.getStartDate()));
			prep.setDate(3, Date.valueOf(coupon.getEndDate()));
			prep.setInt(4, coupon.getAmount());
			prep.setString(5, coupon.getType().toString());
			prep.setString(6, coupon.getMessage());
			prep.setDouble(7, coupon.getPrice());
			prep.setString(8, coupon.getImage());
			
			prep.executeUpdate();
			rs = prep.getGeneratedKeys();
			rs.next();
			id = rs.getLong(1);
			coupon.setId(id);
			
			// Letting the others (if the asking) that the Company Added Succsefully.
			SharingData.setFlag1(true);
			String tostring = coupon.toString();
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
		
		return id;

	} // createCoupon - function

	@Override
	public void removeCoupon(Coupon coupon) {
		
		IsExistDB.idExistV2Coupon(coupon.getId());
		if(IsExistDB.getAnswer2() == true) {

			//ResultSet rs = null;
			PreparedStatement prep = null;
			
			try {
				
				DBConnector.getCon();
				String sqlDELobject = "DELETE FROM coupon WHERE Coup_ID =?";
				prep = DBConnector.getInstance().prepareStatement(sqlDELobject);
				prep.setLong(1, coupon.getId());
				prep.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
			
			finally {
				try {
					DBConnector.getInstance().close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			} // finally
		}
		
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		
       try {

    	   DBConnector.getCon();
			
			
			String sql = "UPDATE Coupon SET Title=?, Start_Date=?, End_Date=?, Amount=?, "
					+ "Category=?, Message=?, Price=?, Image=? WHERE Coup_ID=?";
			PreparedStatement prep = DBConnector.getInstance().prepareStatement (sql);
			prep.setString(1, coupon.getTitle());
			prep.setDate(2, Date.valueOf(coupon.getStartDate()));
			prep.setDate(3, Date.valueOf(coupon.getEndDate()));
			prep.setLong(4, coupon.getAmount());
			prep.setString(5, coupon.getType().toString());
			prep.setString(6, coupon.getMessage());
			prep.setDouble(7, coupon.getPrice());
			prep.setString(8, coupon.getImage());
			prep.setLong(9, coupon.getId());
			
			prep.executeUpdate();
			
		    // Letting the others (if the asking) that the Coupon update Succsefully.
		    SharingData.setFlag1(true);
		    String tostring = coupon.toString();
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
	public Coupon getCoupon(long id) {
	
		Coupon coupon = null;
		String title, message, image;
		Date stDate, enDate ;	
		int amount;
		CouponType type = null;
		double price;
		
		DBConnector.getCon();
		
		try {
			String sqlSEL = "SELECT * FROM Coupon WHERE Coup_ID= ?" ;
			PreparedStatement prep = DBConnector.getInstance().prepareStatement(sqlSEL);
			prep.setLong(1, id);
			
			ResultSet rs = prep.executeQuery();
			rs.next();
			
			title = rs.getString("Title");
			stDate = rs.getDate("start_date");
			enDate = rs.getDate("end_date");
			amount = rs.getInt("amount");
			type = CouponType.valueOf(rs.getString("Category").toUpperCase());
			message = rs.getString("Message");
			price = rs.getDouble("Price");
			image = rs.getString("image");

			coupon = new Coupon(id, title, stDate.toLocalDate(), enDate.toLocalDate(), amount, type,  message, price, image);
			String customerInfo = coupon.toString();
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
		return coupon;
	} // getCoupon - Function

	@Override
	public Collection<Coupon> getAllCoupon() {
		Set<Coupon> coupons = new HashSet<>(); 
		
		DBConnector.getCon();
		
		try {
			String sql = "SELECT coup_id FROM Coupon";
			Statement stat = DBConnector.getInstance().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coupons;
	} // getAllCoupon - function

	@Override
	public Set<Coupon> getCouponByType(CouponType category) {
		
		Set<Coupon> coupons = new HashSet<>();
		
		DBConnector.getCon();
		try {
			String sql = "SELECT coup_id FROM Coupon WHERE Category= '" 
		    + category.toString().toUpperCase() + "'";
			PreparedStatement prep = DBConnector.getInstance().prepareStatement(sql);
			//prep.setString(1, category.toString().toUpperCase());
			ResultSet rs = prep.executeQuery(sql);

			while (rs.next()) {
				coupons.add(getCoupon(rs.getLong(1)));
			}
			
			// Sharing the results as String :)
			String customerInfo = coupons.toString();
			SharingData.setVarchar2(customerInfo);

			// Letting the other Classes (if they asking) that the getID Function was run Succsefully.
			SharingData.setFlag1(true);
			
		} catch (SQLException e) {
			e.getMessage();
		}
		return coupons;
	}

}
