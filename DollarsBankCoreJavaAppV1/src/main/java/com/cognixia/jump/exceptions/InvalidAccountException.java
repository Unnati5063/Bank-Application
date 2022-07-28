package com.cognixia.jump.exceptions;

public class InvalidAccountException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidAccountException() {
		super("Account is not linked with user.");
	}

}
