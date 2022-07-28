package com.cognixia.jump.exceptions;

public class IllegalOptionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public IllegalOptionException() {
		super("Please input a valid option.");
	}
}
