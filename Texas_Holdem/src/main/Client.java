package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

public class Client {
	
	private static Socket soc;
	private final static int SOCKET_NUMBER = 8901;
	private static String IP_ADRESS;
	private static AuctionGUI gui;
	private static String playerName;
	private static BufferedReader in;
	private static PrintWriter out;
	
	
	public static void main(String[] args) {
		
		//create client's GUI, disabled until game starts
		gui = new AuctionGUI();
		
		getIPAdress();
		getPlayerName();
		
		try{
			//create tools for network communication: socket, reader and writer 
			soc = new Socket(IP_ADRESS, SOCKET_NUMBER);
	        in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
	        out = new PrintWriter(soc.getOutputStream(), true);
	        
			
	        //Send your nickname after successful connection
			out.println(playerName);
	        
			
			//set the gui visible, but disabled
			gui.setVisible(true);
			//MoveRestrictions.RestrictAll(gui);
			
			
			//main game loop
			startGame();
		}
		catch(Exception ex){
			
			System.out.println(ex.getMessage());
			System.exit(-1);
		}
	}
	
	
	
	//show dialog collecting IP address of a server
	private static void getIPAdress(){
		IP_ADRESS = JOptionPane.showInputDialog(
				gui,
	            "Enter IP Address of the Server:",
	            JOptionPane.QUESTION_MESSAGE);
	}
	
	
	
	//show dialog collecting player's name
	private static void getPlayerName(){
		playerName = JOptionPane.showInputDialog(
				gui,
	            "Enter your nickname seen by other players:",
	            JOptionPane.QUESTION_MESSAGE);
	}
	
	
	
	private static String[] separatedInput;
	
	private static void startGame(){
		try{
			while(true){
				separatedInput = parseData(in.readLine());
				
				if(separatedInput[0].equals("set active")){
					MoveRestrictions.ResetRestrictions(gui);
				/*	MoveRestrictions.Restrict(gui, 
							Integer.parseInt(separatedInput[2]), 
							Integer.parseInt(separatedInput[5]), 
							ActionTaken.valueOf(separatedInput[3]), 
							Integer.parseInt(separatedInput[4]));*/
					getMovementFromButton();
				}
					
				if(separatedInput[0].equals("set disabled")){
					MoveRestrictions.RestrictAll(gui);
				}
				
				if(separatedInput[0].equals("data")){
					DisplayData(separatedInput);
				}
			}
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(gui, ex.getMessage());
		}
	}
	
	
	
	public static String[] parseData(String input){
		return input.split(";");
	}
	
	
	
	private static void DisplayData(String[] data){
		gui.turn.setText("Ruch gracza: " + data[1]);
		gui.txtStawka.setText(data[5]);
		gui.txtPula.setText(data[6]);
	}
	
	
	private static void getMovementFromButton(){
		//wait for player's action
		try {
			gui.latch.await();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		//send information about player's move
		out.println(gui.actionName);
		//reset player's move state
		gui.actionName = null;
		gui.latch = new CountDownLatch(1);
	}
}
