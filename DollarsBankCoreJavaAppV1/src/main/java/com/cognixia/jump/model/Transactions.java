package com.cognixia.jump.model;

import java.time.LocalDateTime;

import com.cognixia.jump.utility.ColorUtil;

public class Transactions {

	private int custId;
	private String description;
	private double transBalance;
	private LocalDateTime timestamp;
	private int accountId;

	public Transactions() {
		
	}
	
	public Transactions(int custId, String description, double transBalance, LocalDateTime timestamp, int accountId) {
		super();
		this.custId = custId;
		this.description = description;
		this.transBalance = transBalance;
		this.timestamp = timestamp;
		this.accountId = accountId;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTransBalance() {
		return transBalance;
	}

	public void setTransBalance(double transBalance) {
		this.transBalance = transBalance;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return description + "[Customer: " + custId + "].\n" + ColorUtil.GREEN_TEXT
				+ "Account Id - " + accountId
				+ "  Balance - " + transBalance + "\n" + ColorUtil.TEXT_RESET
				+ timestamp.getDayOfWeek() + " "
				+ timestamp.getMonth() + " "
				+ timestamp.getDayOfMonth() + " "
				+ timestamp.getHour() + ":" + timestamp.getMinute() + ":" + timestamp.getSecond() + " CST "
				+ timestamp.getYear() + "\n\n";
	}

}