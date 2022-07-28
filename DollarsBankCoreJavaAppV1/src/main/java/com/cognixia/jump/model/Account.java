package com.cognixia.jump.model;

import java.time.LocalDateTime;

public interface Account {

	int getId();

	void setId(int id);

	double getBalance();

	void setBalance(double balance);

	LocalDateTime getDateCreated();

	String getAccountType();

	void setDateCreated(LocalDateTime dateCreated);


}