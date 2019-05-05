package com.hosts;

import java.io.IOException;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.hosts.client.database.operations.ClientLogin;
import com.hosts.client.database.operations.DBAccessCommand;
import com.hosts.server.dboperations.processor.ServerDBOperationsProcessor;
import com.sketchpad.gui.SketchLine;
import com.esotericsoftware.kryonet.Connection;


/**
 * 
 * @author jairus-main
 *
 */
public class MyServer {
	Server server;
	ServerDBOperationsProcessor dbproc;

	public MyServer() throws IOException {
		server = new Server();
		server.bind(Network.TCP_PORT, Network.UDP_PORT);
		new Thread(server).start();
		Network.register(server);
		dbproc = new ServerDBOperationsProcessor();

		server.addListener(new Listener() {
			SketchLine line;

			// server waits for messages from clients or
			// new client connections
			public void received(Connection c, Object object) {
				
				
				if (object instanceof String) {
					String message = (String)object;
					String[] tokens = message.split(" ");
					
					
					if(tokens[0].equals("login") && tokens.length==3){
						String result = dbproc.process(message);
						System.out.println(result);
						server.sendToTCP(c.getID(), result + " " + tokens[2]);
					}
					
					if(tokens[0].equals("register")){
						// insert into database and validate
						String result = dbproc.process(message);
						server.sendToTCP(c.getID(), result); 
					}
					
					
					
					
					
					
					
					
				}
				
				if (object instanceof SketchLine) {
					line = (SketchLine) object;

					// update all connected clients
					server.sendToAllExceptTCP(c.getID(), line);
				}



			}// end receiving

			public void disconnected(Connection c) {
				System.out.println(c.getID() + " disconnected");
			}

		});


		System.out.println("Server is listening!");
	}

	// This holds per connection state.
	static class MyConnection extends Connection {
		public String name;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
//		new Thread(new Runnable(){
//			
//			@Override
//			public void run() {
//				try {
//					new MyServer();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//		}).start();
//		
//		Thread.sleep(1000);
//		
//		new Thread(new Runnable(){
//			
//			@Override
//			public void run() {
//				try {
//					new MyClient();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// TODO Auto-generated method stub
//			}
//			
//		}).start();
		
		new MyServer();
		
		
	} // end main

}
