package com.cognixia.jump.model;

import com.cognixia.jump.utility.ColorUtil;

public class Customer {

	private int id;
	private String username;
	private String password;
	private String name;
	private String address;
	private String phone;
	
	public Customer() {
		
	}

	public Customer(int id, String username, String password, String name, String address, String phone) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "\n" + "CONTACT INFO:" + "\nName:        \t" + name + "\nAddress:     \t" + address
				+ "\nPhone #:     \t" + phone
				+ "\n\n" + "ACCOUNT INFO:" 
				+ "\nCustomer ID: \t" + id + "\nUsername:    \t" + username
				+ "\nPassword:    \t" + password + "\n";
	}	
}
