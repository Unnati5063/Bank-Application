package com.cognixia.jump.model;

import java.time.LocalDateTime;

public class SavingsAccount implements Account{

	private int id;
	private double balance;
	private LocalDateTime dateCreated;
	private final String accountType = "SAVINGS";

	public SavingsAccount() {
		id = -1;
		balance = 0;
		dateCreated = LocalDateTime.now();
	}
	
	public SavingsAccount(int id, double balance) {
		super();
		this.id = id;
		this.balance = balance;
		dateCreated = LocalDateTime.now();
	}

	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public double getBalance() {
		return balance;
	}
	@Override
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	@Override
	public String getAccountType() {
		return accountType;
	}
	@Override
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "\n[id = " + id  + " | accountType = "
				+ accountType + "]" + "\nBalance: " + balance + "\n";
	}
}