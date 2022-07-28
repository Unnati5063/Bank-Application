package com.cognixia.jump.exceptions;

public class InvalidWithdrawalException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InvalidWithdrawalException() {
		super("Cannot Withdraw balance.");
	}	
}
