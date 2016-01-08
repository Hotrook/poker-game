package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

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
					MoveRestrictions.Restrict(gui, 
							Integer.parseInt(separatedInput[3]), 
							Integer.parseInt(separatedInput[6]), 
							separatedInput[4].equals("null")?ActionTaken.NONE:ActionTaken.valueOf(separatedInput[4]), 
							Integer.parseInt(separatedInput[5]));
					getMovementFromButton();
				}
					
				if(separatedInput[0].equals("set blocked")){
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
				
				if(separatedInput[0].equals("end")){
					JOptionPane.showMessageDialog(gui, "Game has ended, becouse there were no player's left.");
					System.exit(0);
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
	
	
	private static int currentBet = 0;
	private static int playerTokens = 0;
	
	private static void DisplayData(String[] data){
		gui.turn.setText("Ruch gracza: " + data[1]);
		gui.txtStawka.setText(data[5]);
		currentBet = Integer.parseInt(data[5]);
		gui.txtPula.setText(data[6]);
		//players start with index 7
		//7-player1, 8-player1's tokens, 9-player2, ...
		int i = 7;
		for(JTextArea plInfo : gui.players){
			if(i<=data.length-1)
			plInfo.setText(data[i] + '\n' + data[i+1]);
			else
			plInfo.setText("EMPTY CHAIR");
			i+=2;
		}
		
		for(int counter = 7; counter <= data.length; counter++){
			if(data[counter].equals(playerName)){
				gui.playerTokens.setText(data[counter+1].substring(0, data[counter+1].length()-5));
				playerTokens = Integer.parseInt(data[counter+1].substring(0, data[counter+1].length()-5));
				break;
			}
		}
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
		case "0": temp = "2"; break;
		case "1": temp = "3"; break;
		case "2": temp = "4"; break;
		case "3": temp = "5"; break;
		case "4": temp = "6"; break;
		case "5": temp = "7"; break;
		case "6": temp = "8"; break;
		case "7": temp = "9"; break;
		case "8": temp = "10"; break;
		case "9": temp = "J"; break;
		case "10": temp = "Q"; break;
		case "11": temp = "K"; break;
		case "12": temp = "A"; break;
		case " ": temp = " "; break;
		}
		
		return temp;
	}
	
	
	private static String DetermineSuit(String suit){
		String temp = null;
		switch(suit){
		case "0" : temp = "\u2663"; break;
		case "1" : temp = "\u2666"; break;
		case "2" : temp = "\u2665"; break;
		case "3" : temp = "\u2660"; break;
		case " ": temp = " "; break;
		}
		
		return temp;
	}
	
	private static void getMovementFromButton(){
		//wait for player's action
		do{
			
			try {
				gui.latch.await();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			gui.latch = new CountDownLatch(1);
			
		}while(illegalMove(gui));
		//send information about player's move
		
		if( gui.actionName == "bet" || gui.actionName == "raise")
			out.println(gui.actionName + ";" + gui.getCurrentPlayerBet());
		else 
			out.println(gui.actionName);
		//reset player's move state
		gui.actionName = null;
		gui.setCurrentPlayerBet(0);
		gui.latch = new CountDownLatch(1);
	}



	private static boolean illegalMove(AuctionGUI gui2) {
		boolean checkMove = true;
		
		if(gui2.actionName == "bet" && (gui2.getCurrentPlayerBet() > playerTokens || gui2.getCurrentPlayerBet() < 0) )
			checkMove = true;
		else if(gui2.actionName == "raise" && (gui2.getCurrentPlayerBet() >= (playerTokens + currentBet) || gui2.getCurrentPlayerBet() < 0) )
			checkMove = true;
		else
			checkMove = false;
			
		return checkMove;
	}



}
