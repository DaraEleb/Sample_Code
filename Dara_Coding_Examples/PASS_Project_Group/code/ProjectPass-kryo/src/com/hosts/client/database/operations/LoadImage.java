package com.hosts.client.database.operations;

public class LoadImage  extends DBAccessCommand {
	
	
	String imageName;
	
	public LoadImage(String name) {
		imageName = name;
	}

}
