package com.cognixia.jump.dao;

import java.sql.SQLException;

import com.cognixia.jump.model.Customer;

public interface CustomerDAO {

	
	public boolean login(String username, String password) throws SQLException;

	public Customer getUser();

	public boolean uniqueUsername(String temp);

	public boolean addCustomer(Customer newCust);

	public void setUser(Customer user);
}
