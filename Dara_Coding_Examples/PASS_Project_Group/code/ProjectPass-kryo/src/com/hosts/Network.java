
package com.hosts;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.hosts.client.database.operations.ClientLogin;
import com.hosts.client.database.operations.CreateAccount;
import com.hosts.client.database.operations.DBAccessCommand;
import com.hosts.client.database.operations.LoadImage;
import com.hosts.client.database.operations.SaveImage;
import com.sketchpad.gui.SketchLine;

/**
 *  This class is a convenient place to keep things common to both the client and server.
 * @author jairus-main
 *
 */
public class Network {
	static public final int port = 54555;
	public static final String SERVER_IP = "localhost";
	public static final int TCP_PORT = 4444;
	public static final int UDP_PORT = 5555;
	public static final int CLIENT_TIMEOUT = 200; // ms
	// aside 54555 54777	
	
	/**
	 *  This registers objects that are going to be sent over the network.
	 * @param endPoint
	 */
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(SketchLine.class);
		kryo.register(String[].class);
		
		kryo.register(ClientLogin.class);
		kryo.register(CreateAccount.class);
		kryo.register(DBAccessCommand.class);
		kryo.register(LoadImage.class);
		kryo.register(SaveImage.class);
		kryo.register(Object.class);
	}

}
