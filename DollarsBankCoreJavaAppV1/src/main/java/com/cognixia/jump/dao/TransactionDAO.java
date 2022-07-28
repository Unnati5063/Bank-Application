package com.cognixia.jump.dao;

import java.sql.SQLException;
import java.util.List;

import com.cognixia.jump.model.Transactions;

public interface TransactionDAO {

	public void setTransactions(int id) throws SQLException;

	public boolean addTransaction(String username, Transactions newTrans);
	
	public List<Transactions> getTransactions();

	void signOut();

	public boolean addTransaction(Transactions with);

}
