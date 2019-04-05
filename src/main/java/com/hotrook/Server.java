package com.hotrook;


import com.hotrook.exceptions.InvalidNumberOfRankException;
import com.hotrook.exceptions.InvalidNumberOfSuitException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
	
	//private static GameType gameType;
	private static ServerSocket listener;
	public static Input input ;
	private static List<Player> players;
	private final static int SOCKET_NUMBER = 8901;
	public static List<PrintWriter> writers = new ArrayList<PrintWriter>();
	public static List<BufferedReader> readers = new ArrayList<BufferedReader>();
	
	
	public static void  main(String[] args) throws IOException{
		
		listener = new ServerSocket(SOCKET_NUMBER);
		System.out.println("Server is running" );
		
		getInputData();
		connectPlayers();
		
		
		//Let's play
		try {
			Game game = new Game(players);
			System.out.println("Starting game");
			game.play();
		} catch (InvalidNumberOfRankException e) {
			System.out.println(e.getMessage());
		} catch (InvalidNumberOfSuitException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	private static void connectPlayers() throws IOException {
		int counter = 0 ; 
		players = new ArrayList<Player>();
		
		System.out.println("Waiting for players...");
		
		while( counter < input.getNumberOfPlayers()){
			Player player = new Player(listener.accept(), input.getInitialTokens(), counter, input.getGameType() ,false );
	        player.setPlayerName(readers.get(player.getPlayerIndex()).readLine());
			System.out.println("Connected player " + player.getPlayerName());
			players.add(player);
			counter++;
		}
		while( counter < input.getNumberOfPlayers() + input.getNumberOfBots()){
			Bot player = new Bot( null, input.getInitialTokens(), counter, input.getGameType() , true);
			System.out.println("Bots are connected");
			players.add(player);
			counter++;
		}
		
	}




	private static void getInputData() {
		
		input = new Input();
		Scanner in = new Scanner(System.in);
		int numOfPlayers = 0;
		int data = 0;
		System.out.println("Wybiesz typ ");
		System.out.println("	1. No-limit");
		System.out.println("	2. Fix-limit");
		System.out.println("	3. Pot-limit");
		System.out.println("Podaj numer: ");
		
		data = in.nextInt();
		
		while ( data != 1 && data != 2 && data != 3 ){
			System.out.println("Sprobuj jeszcze raz: ");
			data = in.nextInt();
		}
		
		if( data == 1 ){
			input.setGameType(GameType.NOLIMIT);
		}
		else if(data == 2){
			input.setGameType(GameType.FIXLIMIT);
			System.out.println("Podaj limit stawki");
			data = in.nextInt();
			input.setLimit(data);
		}
		else if(data == 3){
			input.setGameType(GameType.POTLIMIT);
		}
		
		
		System.out.println("Podaj liczbe graczy(2-10): ");
		
		numOfPlayers = in.nextInt();
		
		while( numOfPlayers < 0  || numOfPlayers > 10){
			System.out.println("Sprobuj jeszcze raz: ");
			numOfPlayers = in.nextInt();
		}
		
		input.setNumberOfPlayers(numOfPlayers);
		
		if ( numOfPlayers != 10 ){
			System.out.println("Podaj liczbe botow, ale pamietaj, ze laczna suma "
					+ "graczy i botow nie moze byc wieksza niz 10: ");
			
			data = in.nextInt();
			
		
			while( data + numOfPlayers > 10 ){
				System.out.println("Sprobuj jeszcze raz: ");
				data = in.nextInt();
			}
			
			input.setNumberOfBots(data);
		}
		else{
			input.setNumberOfBots(0);
		}
		
		System.out.println("Podaj z jaka stawka zaczynaja gracze: ");
		data = in.nextInt();
		
		input.setInitialTokens(data);
		
		
		System.out.println("Podaj wartosc duzej ciemnej: ");
		data = in.nextInt();
		
		input.setBigBlindValue(data);
		
		
		System.out.println("Podaj wartosc malej ciemnej: ");
		data = in.nextInt();
		
		input.setSmallBlindValue(data);
		
		
		in.close();
	}

}
