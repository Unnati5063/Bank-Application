package com.cognixia.jump.exceptions;

public class PasswordIncorrectException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public PasswordIncorrectException() {
		super("Username / Password is incorrect");
	}

}
