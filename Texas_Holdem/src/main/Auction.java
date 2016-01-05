package main;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class Auction{
	private List<Player> playerQueue;
	private List<Player> playersInRound;
	private int roundNumber;
	
	private int currentPot;
	private int currentBet;
	
	public Auction(List<Player> playersInRound){
		setCurrentPot(0);
		setRoundNumber(0);
		playerQueue = new ArrayList<Player>();
		setPlayersInRound(playersInRound);
	}

////METHODS USED IN AUCTION////
	
	public void setInitialPlayerQueue(List<Player> players){
		boolean playerAfterBigBlind = false;
		int firstAddedPlayer = 0;
		int index = 0;
		for ( Player player : players){
			if(player.isBigBlind()){
				firstAddedPlayer = index;
				playerAfterBigBlind = true;
			}
			else if(playerAfterBigBlind == true){
				playerQueue.add(player);
			}
		index++;
		}
		for(int i=0;i<=firstAddedPlayer;i++){
			playerQueue.add(players.get(i));
		}
	}
	
	public void setPlayerQueue(List<Player> players){
		boolean playerAfterSmallBlind = false;
		int firstAddedPlayer = 0;
		int index = 0;
		for ( Player player : players){
			if(player.isSmallBlind()){
				playerQueue.add(player);
				firstAddedPlayer = index;
				playerAfterSmallBlind = true;
			}
			else if(playerAfterSmallBlind == true){
				playerQueue.add(player);
			}
		index++;
		}
		for(int i=0;i<firstAddedPlayer;i++){
			playerQueue.add(players.get(i));
		}
	}
	
	public boolean checkIfBetsAreEqual(List<Player> players){
		boolean temp = true;
		int checkingBet = players.get(0).getCurrentBet();
		for(int i=1; i<players.size(); i++){
			if(players.get(i).getCurrentBet() != checkingBet){
				temp = false;
				break;
			}
			checkingBet = players.get(i).getCurrentBet();
		}
		return temp;
	}
	
	//method used to send data from each player to his client
	private void sendDataToEachClient(List<Player> players){
		for(PrintWriter writer : Server.writers){
			writer.println(createDataPackage(players));
		}
	}
	
	
	private void sendHandInfoToEachClient(List<Player> players){
		for(Player player : players){
			player.sendHandInfoToClient();
		}
	}
	
	//modify this method when more data is needed on client side
	//the data format is: x;y;z
	// ';' as a default delimiter
	//package contains: "data", current player's name, current bet, current pot, each player's (left in game) name and tokens
	//0-data,1-CPname,2-CPtokens,3-PPaction,4-moves_counter,5-bet,6-pot,7-...
	public String createDataPackage(List<Player> players){
		String data;
		data = "data";
		data += ";" + getCurrentPlayer().getPlayerName();
		data += ";" + getCurrentPlayer().getPlayerTokens();
		data += ";" + getPreviousPlayer().getActionName();
		data += ";" + movesCounter;
		data += ";" + getCurrentBet();
		data += ";" + getCurrentPot();
		
		for(Player pl : players){
			data += ";" + pl.getPlayerName();
			data += ";" + pl.getPlayerTokens();
		}
		return data;
	}
////ACTUAL METHOD FOR STARTING AUCTION////	
	private boolean endOfAuction = false;
	private Player currentPlayer = null;
	private Player previousPlayer = null;
	public int movesCounter = 0;
	
	public void startAuction(int round){
		endOfAuction = false;
		if(round!=0)
			setPlayerQueue(playersInRound); //set player queue with players in round
		else
			setInitialPlayerQueue(playersInRound);

			previousPlayer = playerQueue.get(0);
			ListIterator<Player> it;// = playerQueue.listIterator();
		while(endOfAuction!=true){ //while everyone makes his move and all player's bets are equal
			it = playerQueue.listIterator();
			while(it.hasNext()){
			//for(Player player : playerQueue){
				

				if(movesCounter >= playerQueue.size()-1 && checkIfBetsAreEqual(playerQueue) == true){	//if everyone took his turn and all player's bets are equal
					endOfAuction=true;
					break;
				}
				
				Player player = it.next();
				currentPlayer = player;
				
				//send data seen on table to each player in game 
				sendDataToEachClient(playerQueue);
				
				//send hand info to each player
				sendHandInfoToEachClient(playerQueue);
				
				//activate current player
				currentPlayer.setActive(createDataPackage(playerQueue));
				
				
				//TODO: add big and small blind to the pot, etc 
				

				switch(player.getActionName()){
		        case "check": player.Check(); break;
		        case "call": player.Call(getCurrentBet()); break;
		        case "bet": player.Bet(20); break; //TODO: get bet value from GUI
		        case "raise": player.Raise(getCurrentBet(), 30); break;//TODO: get raise value from GUI
		        case "fold": player.Fold(); break;
		        case "allin": player.AllIn(); break;
		        default: break;
		        }
		        
				if(player.playerState == ActionTaken.CHECKING){ 
					//do nothing important
				}			
				if(player.playerState == ActionTaken.BETING){
					setCurrentBet(player.getCurrentTotalBet());
					setCurrentPot(player.getCurrentTotalBet());
				}
				if(player.playerState == ActionTaken.CALLING){
					setCurrentPot(getCurrentPot() + player.getCurrentTotalBet());
				}				
				if(player.playerState == ActionTaken.RISING){ 
					setCurrentBet(player.getCurrentTotalBet());
					setCurrentPot(getCurrentPot() + player.getCurrentTotalBet()); 
				}
				if(player.playerState == ActionTaken.FOLDING){ 
					playersInRound.remove(player); //if player is folding, remove him from this round and queue
					playerQueue.remove(player);
				}
				if(player.playerState == ActionTaken.ALLIN){ 
					setCurrentPot(getCurrentPot() + player.getCurrentTotalBet());
					if(player.getCurrentTotalBet() > getCurrentPot())
						setCurrentBet(player.getCurrentTotalBet());
					player.setPlayerTokens(0);
					playerQueue.remove(player);
				}
				currentPlayer.setBlocked();
				getCurrentPlayer().setActionName(null);
				movesCounter++;
				previousPlayer = currentPlayer;
				Messenger.getInstance().setCurrentPot(getCurrentPot(),playerQueue);
				Messenger.getInstance().setCurrentBet(getCurrentBet(),playerQueue);
			}
			movesCounter = 0;
		}
		if(endOfAuction==true)
			System.out.println("Koniec aukcji!");
		//reseting values before next auction
		//*current bet
		setCurrentBet(0);
		//*player queue 
		playerQueue = new ArrayList<Player>();
		//*player state for each player left in round
		for(Player player : playersInRound){
			player.playerState = null;
			player.setCurrentBet(0);
		}
		//current pot should be reset in round, after saving it's value
	}
	
////GETTERS AND SETTERS////
	
	public int getCurrentPot() {
		return currentPot;
	}

	public void setCurrentPot(int currentPot) {
		this.currentPot = currentPot;
	}

	public int getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	
	public List<Player> getPlayersInRound() {
		return playersInRound;
	}

	public void setPlayersInRound(List<Player> playersInGame) {
		this.playersInRound = playersInGame;
	}
	
	public List<Player> getPlayerQueue(){
		return playerQueue;
	}
	
	public Player getCurrentPlayer(){
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player player){
		this.currentPlayer = player;
	}

	public Player getPreviousPlayer() {
		return previousPlayer;
	}

	public void setPreviousPlayer(Player previousPlayer) {
		this.previousPlayer = previousPlayer;
	}

}
