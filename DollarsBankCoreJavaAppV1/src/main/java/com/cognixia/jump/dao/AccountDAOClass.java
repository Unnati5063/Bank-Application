package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.application.DollarsBankApplication;
import com.cognixia.jump.model.Account;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.SavingsAccount;

public class AccountDAOClass implements AccountDAO{
	
	private Connection conn;
	
	public AccountDAOClass() {
		conn = DollarsBankApplication.conn;
	}
	
	private List<Account> accounts;
	
	@Override
	public List<Account> getAccounts() {
		return accounts;
	}
	@Override
	public void setAccounts(int userId) throws SQLException{
		
		accounts = new ArrayList<Account>();
		
		PreparedStatement pstmt = conn.prepareStatement("select * from account where cust_id = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		pstmt.setInt(1, userId);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			accounts.add(new SavingsAccount(rs.getInt("id"), rs.getDouble("balance")));
		}
		rs.close();
		pstmt.close();
	}

	@Override
	public boolean addAccount(String username, Account acc) {
		try {
			PreparedStatement prestmt = conn.prepareStatement("SELECT * FROM customer WHERE username = ?");
			prestmt.setString(1, username);
			
			ResultSet rs = prestmt.executeQuery();
			
			int userId = 0;
			if(rs.next()) {
				userId = rs.getInt("id");
			}
			else {
				System.out.println("Please Terminate Program");
				return false;
			}

			rs.close();
			prestmt.close();
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO account(balance, date_created, type, cust_id) VALUES (?, ?, ?, ?)");
			pstmt.setDouble(1, acc.getBalance());
			pstmt.setString(2, acc.getDateCreated().toString());
			pstmt.setString(3, acc.getAccountType());
			pstmt.setInt(4, userId);
			
			int result = pstmt.executeUpdate();
			
			pstmt.close();
			
			if (result > 0) {
				return true;
			}
		}catch(SQLException e) {
			System.out.println("Please Terminate Program");
		}
		return false;
	}
	@Override
	public boolean addAccount(int userId, Account acc) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO account(balance, date_created, type, cust_id) VALUES (?, ?, ?, ?)");
			pstmt.setDouble(1, acc.getBalance());
			pstmt.setString(2, acc.getDateCreated().toString());
			pstmt.setString(3, acc.getAccountType());
			pstmt.setInt(4, userId);
			
			int result = pstmt.executeUpdate();
			
			pstmt.close();
			
			if (result > 0) {
				setAccounts(userId);
				return true;
			}
		}catch(SQLException e) {
			System.out.println("Please Terminate Program");
		}
		return false;
	}
	
	@Override
	public boolean updateAccountBalance(Account acc) {
		
		try {			
			PreparedStatement pstmt = conn.prepareStatement("UPDATE account SET balance = ? WHERE id = ?");
			pstmt.setDouble(1, acc.getBalance());
			pstmt.setInt(2, acc.getId());
			
			int result = pstmt.executeUpdate();
			
			pstmt.close();
			
			if (result > 0) {
				return true;
			}
			
		}catch (SQLException e) {
			System.out.println("Error Occurred");
		}
		
		return false;
	}

	@Override
	public void signOut() {
		accounts = null;
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Account s : accounts) {
			result = result + s.toString();
		}
		return result;
	}
	@Override
	public String transferToString(int option) {
		String result = "";
		for (Account s : accounts) {
			if (!(s.getId() == option)) {
				result = result + s.toString();
			}
		}
		return result;
	}
}