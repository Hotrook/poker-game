package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
	
	//private static GameType gameType;
	private static ServerSocket listener;
	private static Input input ;
	private static List<Player> players;
	private final static int SOCKET_NUMBER = 8901;
	
	
	
	public static void  main(String[] args) throws IOException{
		
		listener = new ServerSocket(SOCKET_NUMBER);
		System.out.println("Server is running" );
		
		getInputData();
		connectPlayers();
		
	}
	
	
	
	
	private static void connectPlayers() throws IOException {
		int counter = 0 ; 
		players = new ArrayList<Player>();
		
		
		while( counter < input.getNumberOfPlayers()){
			Player player = new Player(listener.accept(), input.getInitialTokens(), counter, input.getGameType() );
			players.add(player);
			counter++;
		}
		while( counter < input.getNumberOfPlayers() + input.getNumberOfBots()){
			Player player = new Bot( null, input.getInitialTokens(), counter, input.getGameType() );
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
			System.out.println("Spróbuj jeszcze raz: ");
			data = in.nextInt();
		}
		
		if( data == 1 ){
			input.setGameType(GameType.NOLIMIT);
		}
		else if(data == 2){
			input.setGameType(GameType.FIXLIMIT);
		}
		else if(data == 3){
			input.setGameType(GameType.POTLIMIT);
		}
		
		
		System.out.println("Podaj liczbę graczy(2-10): ");
		
		numOfPlayers = in.nextInt();
		
		while( numOfPlayers < 2  || numOfPlayers > 10){
			System.out.println("Spróbuj jeszcze raz: ");
			numOfPlayers = in.nextInt();
		}
		
		input.setNumberOfPlayers(numOfPlayers);
		
		if ( numOfPlayers != 10 ){
			System.out.println("Podaj liczbę botów, ale pamiętaj, że łączna suma "
					+ "graczy i botów nie może być większa niż 10: ");
			
			data = in.nextInt();
			
		
			while( data + numOfPlayers <= 10 ){
				System.out.println("Spróbuj jeszcze raz: ");
				data = in.nextInt();
			}
			
			input.setNumberOfBots(data);
		}
		else{
			input.setNumberOfBots(0);
		}
		
		System.out.println("Podaj z jaką stawką zaczynają gracze: ");
		data = in.nextInt();
		
		input.setInitialTokens(data);
		
		
		
		
		
		
		
		
	}

}
