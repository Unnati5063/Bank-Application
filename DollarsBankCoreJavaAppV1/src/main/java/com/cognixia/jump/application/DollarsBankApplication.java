package com.cognixia.jump.application;

import java.sql.Connection;
import java.sql.SQLException;

import com.cognixia.jump.controller.DollarsBankController;

public class DollarsBankApplication {
	
	public static Connection conn;
	
	public static void main(String[] args) {
		
		conn = ConnManagerWithProperties.getConnection();
		
		if (conn == null) {
			System.out.println("connection made to DB");
			System.out.println("Now exiting !!!" );
			return;
		}
		
		DollarsBankController ctl = new DollarsBankController();
		ctl.startUp();
		
		try {
			conn.close();
			exitMessage();
		} catch (SQLException e) {
			System.out.println("Connection not closing");
			e.printStackTrace();
		}
	}
	public static void greeting() {
		System.out.println("+---------------------------+");
		System.out.println("| DOLLARSBANK Welcomes You! |");
		System.out.println("+---------------------------+");
		System.out.println("1. Create New Account");
		System.out.println("2. Login");
		System.out.println("3. Exit\n");
		
		System.out.println("Enter Choice (1, 2, or 3)");
	}
	
	public static void menuMakeAccount() {
		System.out.println("+-------------------------------+");
		System.out.println("| Enter Details For New Account |");
		System.out.println("+-------------------------------+");
		
	}
	
	public static void loginMenu() {
		System.out.println("+---------------------+");
		System.out.println("| Enter Login Details |");
		System.out.println("+---------------------+");
		
	}
	
	public static void mainMenu() {
		System.out.println("+---------------------+");
		System.out.println("| WELCOME Customer |");
		System.out.println("+---------------------+");
		System.out.println("1. Deposit Amount");
		System.out.println("2. Withdraw Amount");
		System.out.println("3. Funds Transfer");
		System.out.println("4. View 5 Recent Transactions");
		System.out.println("5. Display Customer Information");
		System.out.println("6. Open New Account");
		System.out.println("7. Sign Out\n");
		
		System.out.println("Enter Choice (1, 2, 3, 4, 5, 6, or 7)");
	}
	
	public static void exitMessage() {
		System.out.println("\n#########################");
		System.out.println("# Thank you !!!           #");
		System.out.println("###########################\n");
	}
	
	public static void newAccountMenu() {
		System.out.println("+------------------+");
		System.out.println("| Open New Account |");
		System.out.println("+------------------+");
		System.out.println("1. Open Savings Account");
		System.out.println("2. Open Checking Account\n");
		
		System.out.println("Enter Choice (1 or 2)");
	}
}