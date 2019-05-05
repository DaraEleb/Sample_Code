package com.hosts.client.database.operations;

public class ClientLogin extends DBAccessCommand {
	
	private String user;
	private String pass;
	private boolean valid;
	
	
	public String getUser() {
		return user;
	}


	public String getPass() {
		return pass;
	}


	public ClientLogin(String user, String pass) {
		this.user = user;
		this.pass = pass;
		valid = false;
	}
	
	public void setValid(boolean v){
		valid = v;
	}
	
	public boolean isValid(){
		return valid;
	}


}
