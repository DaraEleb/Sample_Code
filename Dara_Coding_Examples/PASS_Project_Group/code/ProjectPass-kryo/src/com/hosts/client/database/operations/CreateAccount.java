package com.hosts.client.database.operations;

public class CreateAccount extends DBAccessCommand {

	
	private String user;
	private String pass;
	
	
	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}


	public CreateAccount(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	

}
