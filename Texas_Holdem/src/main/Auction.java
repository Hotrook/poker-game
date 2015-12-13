package main;

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
			}
			else if(playerQueue.isEmpty()==false){
				playerQueue.add(player);
			}
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
////ACTUAL METHOD FOR STARTING AUCTION////	
	private boolean endOfAuction = false;
	private int auctionCounter = 0;
	private Player currentPlayer = null;
	
	public void StartAuction(int round){
		endOfAuction = false;
		if(round!=0)
			setPlayerQueue(playersInRound); //set player queue with players in round
		else
			setInitialPlayerQueue(playersInRound);

			ListIterator<Player> it = playerQueue.listIterator();
		while(endOfAuction!=true){ //while everyone makes his move and all player's bets are equal
			if(it.hasNext()){
				Player player = it.next();
				currentPlayer = player;
				if(auctionCounter > 0 && checkIfBetsAreEqual(playerQueue) == true){	//if everyone took his turn and all player's bets are equal
					endOfAuction=true;
					break;
				}
				
				//TODO: add big and small blind to the pot, etc 
				
				MoveRestrictions.Restrict(); //TODO: implement that class
				player.getMovement(); //get movement from server 
				if(player.playerState == ActionTaken.CHECKING){ 
					continue;
				}			
				if(player.playerState == ActionTaken.BETING){
					setCurrentBet(player.getCurrentBet());
					setCurrentPot(player.getCurrentBet());
				}
				if(player.playerState == ActionTaken.CALLING){
					player.setCurrentBet(getCurrentBet());
					player.setPlayerTokens(player.getPlayerTokens() - getCurrentBet());
					setCurrentPot(getCurrentPot() + player.getCurrentBet());
				}				
				if(player.playerState == ActionTaken.RISING){ 
					player.setPlayerTokens(player.getPlayerTokens() - getCurrentBet());
					player.setCurrentBet(getCurrentBet() + getCurrentBet());
					setCurrentBet(player.getCurrentBet());
					setCurrentPot(getCurrentPot() + player.getCurrentBet()); 
				}
				if(player.playerState == ActionTaken.FOLDING){ 
					playersInRound.remove(player); //if player is folding, remove him from this round and queue
					it.remove();
				}
				if(player.playerState == ActionTaken.ALLIN){ 
					setCurrentPot(getCurrentPot() + player.getCurrentBet());
					if(player.getCurrentBet() > getCurrentPot())
						setCurrentBet(player.getCurrentBet());
					player.setPlayerTokens(0);
					it.remove();
				}
			}
			auctionCounter++; 	
		}
		//reseting values before next auction
		//*current bet
		setCurrentBet(0);
		//*player queue 
		playerQueue = null;
		//*player state for each player left in round
		for(Player player : playersInRound){
			player.playerState = null;
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

}
