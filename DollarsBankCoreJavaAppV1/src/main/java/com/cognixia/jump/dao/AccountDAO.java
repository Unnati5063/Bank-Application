package com.cognixia.jump.dao;

import java.sql.SQLException;
import java.util.List;

import com.cognixia.jump.model.Account;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.SavingsAccount;

public interface AccountDAO {

	public void setAccounts(int userId) throws SQLException;

	public boolean addAccount(String username, Account acc);
	
	boolean addAccount(int userId, Account acc);

	public void signOut();

	public List<Account> getAccounts();

	boolean updateAccountBalance(Account acc);

	public String transferToString(int option);

}
