package com.cognixia.jump.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cognixia.jump.application.DollarsBankApplication;
import com.cognixia.jump.dao.AccountDAO;
import com.cognixia.jump.dao.AccountDAOClass;
import com.cognixia.jump.dao.CustomerDAO;
import com.cognixia.jump.dao.CustomerDAOClass;
import com.cognixia.jump.dao.TransactionDAO;
import com.cognixia.jump.dao.TransactionDAOClass;
import com.cognixia.jump.exceptions.IllegalOptionException;
import com.cognixia.jump.exceptions.InvalidAccountException;
import com.cognixia.jump.exceptions.InvalidWithdrawalException;
import com.cognixia.jump.exceptions.PasswordIncorrectException;
import com.cognixia.jump.model.Account;
import com.cognixia.jump.model.CheckingAccount;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.SavingsAccount;
import com.cognixia.jump.model.Transactions;

public class DollarsBankController {
	
	public static Scanner sc = new Scanner(System.in);
	private AccountDAO accDAO = new AccountDAOClass();
	private TransactionDAO transDAO = new TransactionDAOClass();
	private CustomerDAO custDAO = new CustomerDAOClass();
	
	public void startUp() {
		
		do {
			int login = -1;
			DollarsBankApplication.greeting();
			try {
				int option = sc.nextInt();
				sc.nextLine();
				
				switch (option) {
					case 1: 
						newUser();
						break;
					case 2:
						login = existingUser();
						break;
					case 3: 
						break;
					default:
						throw new IllegalOptionException();
				}
				
				if (option == 2) {
					if (login == -1) {
						continue;
					}
					if (login == 0) {
						return;
					}
					if (login == 1) {
						System.out.print("\033[2J");
						session();
					}
				}
				
				if (option == 3) {
					return;
				}
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please input a valid option.\n");
			}
			catch (IllegalOptionException e) {
				System.out.println("Please input a valid option.\n");
			}
			
		}while(true);
	}
	
	private void newUser() {
		Customer newCust = new Customer();
		System.out.println();
		DollarsBankApplication.menuMakeAccount();
		System.out.println("Customer Name:");
		newCust.setName(sc.nextLine());
		
		System.out.println("Customer Address:");
		newCust.setAddress(sc.nextLine());
		
		do {
			System.out.println("Customer Username:");
			String temp = sc.nextLine();
			
			if(custDAO.uniqueUsername(temp)) {
				newCust.setUsername(temp);
				break;
			}
			else {
				System.out.println("Username - Try Again!");
			}
		}while(true);
			
		String phonePattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

		do {
			System.out.println("Customer Number:");
			String temp = sc.nextLine();
			
			if (temp.matches(phonePattern)) {
				newCust.setPhone(temp);
				break;
			}
			else {
				System.out.println("Try Again!");
			}
			
		}while(true);
		
		String passPattern  = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[!@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
		Pattern p = Pattern.compile(passPattern);		
		
		do {
			System.out.println("Password: 8 Characters Min With Lower, Upper, Number, and Special");
			String temp = sc.nextLine();
			temp = temp.replaceAll("\n", "");
			temp = temp.replaceAll("\\s", "");
			Matcher m = p.matcher(temp);
			
			if (m.matches()) {
				newCust.setPassword(temp);
				break;
			}
			else {
				System.out.println("Password - Try Again!");
			}
			
		}while(true);
		
		double min = 500.0;
		SavingsAccount newAcc = new SavingsAccount();
		Transactions newTrans = new Transactions();
		
		do {
			try {
				System.out.println("Initial Deposit Amount: Minimum $500.00 (input #'s only)");
				double temp = sc.nextDouble();
				sc.nextLine();
				
				if (temp >= min) {
					newAcc.setBalance(temp);
					newTrans.setTransBalance(temp);
					newTrans.setTimestamp(LocalDateTime.now());
					newTrans.setDescription("Initial Deposit in account ");
					break;
				}
				else {
					System.out.println("Invalid balance - Try Again!");
				}
			} catch (InputMismatchException e ) {
				System.out.println("Invalid balance - Try Again!");
				sc.nextLine();
			}
			
		}while(true);
		
		if (custDAO.addCustomer(newCust)) {
			if(accDAO.addAccount(newCust.getUsername(), newAcc)) {
				if(transDAO.addTransaction(newCust.getUsername(), newTrans)) {
					System.out.println("\nCongratulations !!! Account Created!\n");
				}
			}
		}
	}
	
	private int existingUser() {
		do {
			DollarsBankApplication.loginMenu();
			System.out.println("[Input 'exit' to return to previous menu]");
			System.out.println("Username:");
			String username = sc.nextLine();
			if (username.equalsIgnoreCase("exit")) {
				return -1;
			}
			
			System.out.println("Password:");
			String password = sc.nextLine();
			if (password.equalsIgnoreCase("exit")) {
				return -1;
			}
			
			try {
				if (!custDAO.login(username, password)) {
					throw new PasswordIncorrectException();
				}
				accDAO.setAccounts(custDAO.getUser().getId());
				transDAO.setTransactions(custDAO.getUser().getId());
				return 1;
				
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println( "Connection Error try again.\n");
				return 0;
			} catch(PasswordIncorrectException e) {
				System.out.println("Invalid Credentials. Try Again!");
				continue;
			}
			
			
		}while (true);
	}
	
	private void session() {
		do {
			System.out.println();
			DollarsBankApplication.mainMenu();
			try {
				int option = sc.nextInt();
				sc.nextLine();
				
				switch (option) {
				case 1: 
					accountAction("Deposit");
					break;
				case 2:
					accountAction("Withdrawal");
					break;
				case 3: 
					if (accDAO.getAccounts().size() < 2) {
						System.out.println("\nCannot perform transfer\n");
					} else {
						accountAction("Transfer");
					}
					break;
				case 4:
					System.out.println("+-----------------------------+");
					System.out.println("| 5 Most Recent Transactions: |");
					System.out.println("+-----------------------------+\n");
					System.out.println(transDAO);
					break;
				case 5: 
					System.out.println(custDAO.getUser());
					System.out.println(accDAO);
					break;
				case 6: 
					openNewAccount();
					break;
				case 7:
					custDAO.setUser(null);
					accDAO.signOut();
					transDAO.signOut();
					System.out.println("\nSigning Out...\n");
					return;
				default:
					throw new IllegalOptionException();
				}
			}catch(InputMismatchException e) {
				System.out.println("\nPlease input a valid option\n");
				sc.nextLine();
			}catch(IllegalOptionException e) {
				System.out.println("\nPlease input a valid option\n");
			}
			
			
		}while(true);
	}
	
	private void accountAction(String action) {
		String menu = action.replaceAll("[a-zA-Z]", "-");
		System.out.println();
		System.out.println("+-" + menu + "--------+");
		System.out.println("| " + action + " Wizard |");
		System.out.println("+-" + menu + "--------+");
		do {
			try {
				String prompt = (action.equals("Transfer")) 
						? "Select Account To Transfer From:" 
						: ("Select Account for " + action);
				
				
				System.out.println("[To cancel " + action + " input -1]");
				System.out.println(prompt);
				System.out.println(accDAO);
				System.out.println("Account ID: ");
				
				int option = sc.nextInt();
				sc.nextLine();
				
				if (option == -1) {
					return;
				}
				Account acc = validUserAccount(option);
				if (acc == null) {
					throw new InvalidAccountException();
				}
				
				System.out.println("\nInput non-zero amount to " + action + ":");
				double amount = sc.nextDouble();
				sc.nextLine();
				
				if (amount <= 0) {
					throw new InputMismatchException();
				}
				
				switch (action) {
				case "Deposit":
					acc.setBalance(amount + acc.getBalance());
					Transactions dep = new Transactions(custDAO.getUser().getId(), "Deposit of " + amount + " for account ", acc.getBalance(), LocalDateTime.now(), acc.getId());
					accDAO.updateAccountBalance(acc);
					transDAO.addTransaction(dep);
					System.out.println("\nSuccessful Deposit For Account " + acc.getId() + "!");
					System.out.println("Current Balance: " + acc.getBalance() + "\n");
					break;
				case "Withdrawal":
					double newbal = acc.getBalance() - amount;
					if (newbal >= 0) {
						acc.setBalance(newbal);
						Transactions with = new Transactions(custDAO.getUser().getId(), "Withdrawal of " + amount + " for account ", acc.getBalance(), LocalDateTime.now(), acc.getId());
						accDAO.updateAccountBalance(acc);
						transDAO.addTransaction(with);
						System.out.println("\nSuccessful Withdrawal For Account " + acc.getId() + "!");
						System.out.println("Current Balance: " + acc.getBalance() + "\n");
					}
					else {
						throw new InvalidWithdrawalException();
					}
					break;
				case "Transfer":
					System.out.println("Select Account To Transfer To:");
					System.out.println(accDAO.transferToString(option));
					System.out.println("Account ID: ");
					
					int transferTo = sc.nextInt();
					sc.nextLine();
					
					Account transferToAcc = validUserAccount(transferTo);
					if (transferToAcc == null) {
						throw new InvalidAccountException();
					}
					double diff = acc.getBalance() - amount;
					if (diff >= 0) {
						acc.setBalance(diff);
						transferToAcc.setBalance(amount);
						Transactions tranfer = new Transactions(custDAO.getUser().getId(), "Transfer from account " + acc.getId() + " to account " + transferToAcc.getId() + " for " + amount + " ", transferToAcc.getBalance(), LocalDateTime.now(), transferToAcc.getId());
						accDAO.updateAccountBalance(acc);
						accDAO.updateAccountBalance(transferToAcc);
						transDAO.addTransaction(tranfer);
						System.out.println("\nSuccessful Trasfer!");
						System.out.println("Transfered From:\n" 
										+ "Account: " + acc.getId() + "\n" 
										+ "Balance: " + acc.getBalance() + "\n");
						System.out.println("Transfered To:\n" 
										+ "Account: " + transferToAcc.getId() + "\n" 
										+ "Balance: " + transferToAcc.getBalance() + "\n");
					}
					break;
				}
				
				return;
				
			} catch(InputMismatchException e) {
				System.out.println("\nPlease choose a listed option.\n");
			}catch (InvalidAccountException e) {
				System.out.println("\nTry Again!");
			}catch (InvalidWithdrawalException e) {
				System.out.println("\nCannot Withdraw account balance.");
			}
		} while(true);
	}

	private void openNewAccount() {
		
		do {
			DollarsBankApplication.newAccountMenu();
			try {
				int option = sc.nextInt();
				sc.nextLine();
				
				System.out.println("Input balance:");
				double newbal = sc.nextDouble();
				sc.nextLine();
				
				if (newbal < 0) {
					System.out.println("\ncannot have negative balance.\n");
					continue;
				}
			
				switch (option) {
				case 1: 
					openAccountUtil(new SavingsAccount(-1, newbal));
					break;
				case 2: 
					openAccountUtil(new CheckingAccount(-1, newbal));
					break;
				default: 
					throw new IllegalOptionException();
				}
				
				System.out.println("\nAccount Opened!\n");
				return;
			} catch (InputMismatchException e) {
				System.out.println("\nPlease input a valid option\n");
				sc.nextLine();
			} catch(IllegalOptionException e) {
				System.out.println("\nPlease input a valid option\n");
			}
		} while(true);
	}
	
	private void openAccountUtil(Account acc) {
		Transactions trans;
		int accId;
		accId = accDAO.getAccounts().get(accDAO.getAccounts().size() - 1).getId();
		trans = new Transactions(custDAO.getUser().getId(), "Initial Deposit Amount in account ", acc.getBalance(), LocalDateTime.now(), accId);
		accDAO.addAccount(custDAO.getUser().getId(), acc);
		transDAO.addTransaction(trans);
	}
	
	private Account validUserAccount(int searchId) {
		Account result = null;
		for (Account s : accDAO.getAccounts()) {
			if (s.getId() == searchId) {
				result = s;
				break;
			}
		}
		return result;
	}
	
}
