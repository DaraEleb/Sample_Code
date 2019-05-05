package com.hosts.client.inputs.gui;

import javax.swing.JOptionPane;

import com.hosts.client.database.operations.ClientLogin;

public class ClientLogger {
	
	private ClientLogin login;
	
	public ClientLogger() {
		String name = JOptionPane.showInputDialog("enter username");
		String pass = JOptionPane.showInputDialog("enter password");
		
		login = new ClientLogin(name,  pass);
	}
	
	
	public ClientLogin getClientLogin(){
		return login;
	}

}
