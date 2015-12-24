package main;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * 
 * @author Seba
 * This class is used to communication between server and particular classes,
 * which need some information from clients. 
 * This class should be as considered as using of Facade Design Pattern
 */
public class Messenger {

	static Messenger messenger;
	BufferedReader input;
    PrintWriter output;
    
	private Messenger() {
		
	}

	public static Messenger getInstance() {
		if( Messenger.messenger == null ){
			synchronized(Deck.class){
				if(  Messenger.messenger == null )
					Messenger.messenger = new Messenger();
			}
		}
		return Messenger.messenger;
	}

	public void setCurrentPot(int currentPot, Object players) {
		// TODO Auto-generated method stub
		
	}

	public void setCurrentBet(int currentBet, List<Player> playerQueue) {
		// TODO Auto-generated method stub
		
	}

}
