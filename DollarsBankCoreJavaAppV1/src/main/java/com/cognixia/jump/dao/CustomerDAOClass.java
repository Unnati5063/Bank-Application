package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognixia.jump.application.DollarsBankApplication;
import com.cognixia.jump.model.Customer;

public class CustomerDAOClass implements CustomerDAO{
	
	private Connection conn;
	
	public CustomerDAOClass() {
		conn = DollarsBankApplication.conn;
	}
	
	private Customer user;
	
	public Customer getUser() {
		return user;
	}

	@Override
	public void setUser(Customer user) {
		this.user = user;
	}

	@Override
	public boolean login(String username, String password) throws SQLException {

		PreparedStatement pstmt = conn.prepareStatement("select * from customer where username = ? and password = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		pstmt.setString(1, username);
		pstmt.setString(2, password);

		ResultSet rs = pstmt.executeQuery();
		
		if (!rs.next()) {
			return false;
		}
		
		rs.first();
		user = new Customer(rs.getInt("id"), rs.getString("username"),
					rs.getString("password"), rs.getString("name"),
					rs.getString("address"), rs.getString("phone"));
		rs.close();
		pstmt.close();
		return true;
	}
	
	@Override
	public boolean uniqueUsername(String username) {
		try {
			int count = 0;
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM customer WHERE username = ?");
			pstmt.setString(1, username);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				count++;
			}
			
			rs.close();
			pstmt.close();
			
			if (count > 0) {
				return false;
			}
			else {
				return true;
			}
		} catch(SQLException e) {
			System.out.println("Error Occured");
			return false;
		}
	}

	@Override
	public boolean addCustomer(Customer user) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO customer(username,name,password,address,phone) VALUES (?, ?, ?, ?, ?)");
			
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getAddress());
			pstmt.setString(5, user.getPhone());
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				return true;
			}
		} catch(SQLException e) {
			System.out.println("Error Occurred");
			return false;
		}
		return false;
	}
}