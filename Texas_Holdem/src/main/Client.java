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
					/*MoveRestrictions.Restrict(gui, 
							Integer.parseInt(separatedInput[3]), 
							Integer.parseInt(separatedInput[6]), 
							separatedInput[4].equals("null")?ActionTaken.NONE:ActionTaken.valueOf(separatedInput[4]), 
							Integer.parseInt(separatedInput[5]));*/
					getMovementFromButton();
				}
					
				if(separatedInput[0].equals("set disabled")){
					MoveRestrictions.RestrictAll(gui);
				}
				
				if(separatedInput[0].equals("data")){
					DisplayData(separatedInput);
				}
				
				if(separatedInput[0].equals("hand")){
					DisplayPlayerHand(separatedInput);
				}
				
				if(separatedInput[0].equals("table cards")){
					DisplayTableCards(separatedInput);
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
	
	
	
	private static void DisplayPlayerHand(String[] cards){
		gui.playerCard1.setText(DetermineRank(cards[1]) + DetermineSuit(cards[2]));
		gui.playerCard2.setText(DetermineRank(cards[3]) + DetermineSuit(cards[4]));
	}
	
	
	
	private static void DisplayTableCards(String[] cards){
		gui.card1.setText(DetermineRank(cards[1]) + DetermineSuit(cards[2]));
		gui.card2.setText(DetermineRank(cards[3]) + DetermineSuit(cards[4]));
		gui.card3.setText(DetermineRank(cards[5]) + DetermineSuit(cards[6]));
		
		if(cards.length == 7){
			gui.card4.setText(null);
			gui.card5.setText(null);
		}
		else if(cards.length == 9){
			gui.card4.setText(DetermineRank(cards[7]) + DetermineSuit(cards[8]));
			gui.card5.setText(null);
		}
		else if(cards.length == 11){
			gui.card4.setText(DetermineRank(cards[7]) + DetermineSuit(cards[8]));
			gui.card5.setText(DetermineRank(cards[9]) + DetermineSuit(cards[10]));
		}
	}
	
	
	private static String DetermineRank(String rank){
		String temp = null;
		switch(rank){
		case "0": temp = "1"; break;
		case "1": temp = "2"; break;
		case "2": temp = "3"; break;
		case "3": temp = "4"; break;
		case "4": temp = "5"; break;
		case "5": temp = "6"; break;
		case "6": temp = "7"; break;
		case "7": temp = "8"; break;
		case "8": temp = "9"; break;
		case "9": temp = "10"; break;
		case "10": temp = "J"; break;
		case "11": temp = "Q"; break;
		case "12": temp = "K"; break;
		case "13": temp = "A"; break;
		}
		
		return temp;
	}
	
	
	private static String DetermineSuit(String suit){
		String temp = null;
		switch(suit){
		case "0" : temp = "\u2663"; break;
		case "1" : temp = "\u2666"; break;
		case "2" : temp = "\u2764"; break;
		case "3" : temp = "\u2660"; break;
		}
		
		return temp;
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
