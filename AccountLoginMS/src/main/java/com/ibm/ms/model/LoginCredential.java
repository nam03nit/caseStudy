package com.ibm.ms.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoginCredential {
	
	@Id
	String username;
	String password;

	
	public LoginCredential() {
		super();
	}
	
	public LoginCredential(String usr, String pass) {
		super();
		this.username = usr;
		this.password = pass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String usr) {
		this.username = usr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}
	

}
