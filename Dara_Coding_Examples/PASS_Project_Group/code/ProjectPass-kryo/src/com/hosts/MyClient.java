package com.hosts;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.sketchpad.gui.SketchLine;
import com.sketchpad.gui.SketchpadApplet;
import com.sketchpad.gui.SketchpadFrame;

/**
 * 
 * @author jairus-main
 *
 */

public class MyClient {

	SketchpadFrame sketchpad;
	volatile SketchpadApplet applet;
	Client client;

	Queue<SketchLine> messageQueue;

	public MyClient() throws IOException {
		client = new Client();
		new Thread(client).start();
		Network.register(client);

		// connected to the sever at this point
		client.connect(Network.CLIENT_TIMEOUT, Network.SERVER_IP,
				Network.TCP_PORT, Network.UDP_PORT);

		init();

	}

	private void init() {
		messageQueue = new ConcurrentLinkedQueue<SketchLine>();
		sketchpad = new SketchpadFrame();
		sketchpad.setVisible(false);
		applet = sketchpad.getPad();

		// ThreadedListener runs the listener methods on a different thread.
		client.addListener(new ThreadedListener(new Listener() {

			public void connected(Connection connection) {
			}

			public void received(Connection connection, Object object) {
				if (object instanceof String) {
					String message = (String) object;
					String[] tokens = message.split(" ");

					if (message.contains("Login Successful!")) {
						System.out.println(message);
						System.out.println(tokens[2]);
						sketchpad.setVisible(true);
						sketchpad.setFrameTitle("Project PASS: " + tokens[2]);
					}
					
					if (message.contains("Account Created!")) {
						JOptionPane.showMessageDialog(null, "Account Created!");
						sketchpad.setVisible(true);
						sketchpad.setFrameTitle("Project PASS: " + tokens[2]);
					}
					if (message.contains("Duplicate username!")){
						JOptionPane.showMessageDialog(null, message);
						System.exit(0);
					}


					if (message.contains("Invalid login")) {
						JOptionPane.showMessageDialog(null, message);
						System.exit(0);
					}
				}

				if (object instanceof SketchLine) {
					applet.update(object);
				}
				
				
				

				
				
				
			}

			public void disconnected(Connection connection) {
				System.exit(0);
			}
		}));

		new Thread(new Runnable() {

			@Override
			public void run() {
				String[] choices = { "Register", "Login" };
				String input = (String) JOptionPane.showInputDialog(null,
						"Choose now...", "Welcome",
						JOptionPane.QUESTION_MESSAGE, null, // Use
															// default
															// icon
						choices, // Array of choices
						choices[1]);
				if(input==null)
					System.exit(0);

				
				String name = JOptionPane.showInputDialog("enter name:");
				String pass = JOptionPane.showInputDialog("enter pass:");
				
				if(name==null || name.isEmpty() || pass==null|| pass.isEmpty()){
					JOptionPane.showMessageDialog(null,"Invalid inputs, application exited");
					System.exit(0);
				}
				
				String clientCommand = name.trim() + " " + pass.trim();
				
				
				
				if (input.equals("Register")) {
					clientCommand = "register " + clientCommand;
				} else if (input.equals("Login")) {
					clientCommand = "login " + clientCommand;
				}

				client.sendTCP(clientCommand);

				SketchLine line;
				while (true) {
					line = applet.getLine();
					// add to the queue
					if (line != null) {
						client.sendTCP(line);
					}
				}
			}

		}).start();

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new MyClient();
		
	}

}
