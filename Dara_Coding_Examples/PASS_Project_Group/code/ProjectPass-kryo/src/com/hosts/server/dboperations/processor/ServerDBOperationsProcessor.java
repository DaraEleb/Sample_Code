package com.hosts.server.dboperations.processor;

import com.hosts.client.database.operations.ClientLogin;
import com.hosts.client.database.operations.CreateAccount;
import com.hosts.client.database.operations.DBAccessCommand;
import com.hosts.client.database.operations.LoadImage;
import com.hosts.client.database.operations.SaveImage;

import database.SQLiteJDBC;

public class ServerDBOperationsProcessor {

	
	private SQLiteJDBC db;
	
	
	
	public ServerDBOperationsProcessor() {
		db = new SQLiteJDBC();
	}
	
	
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public String process(String input) {
		String result = "";
		
		String[] in = input.split(" ");
		
		if(in[0].equalsIgnoreCase("login")){
			boolean res =  db.validateUser(in[1], in[2]);
			if(res) result = "Login Successful!";
			else result = "Invalid login";
			
		}
		
		if(in[0].equalsIgnoreCase("register")){
			boolean res =  db.register(in[1], in[2]);
			if(res) result = "Account Created! " + in[1];
			else result = "Duplicate username! " + in[1];
			
		}
		
		
		
		return result;
	}

}