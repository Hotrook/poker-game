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
////ACTUAL METHOD FOR STARTING AUCTION////	
	private boolean endOfAuction = false;
	private int auctionCounter = 0;
	private Player currentPlayer = null;
	private Player previousPlayer = null;
	public int movesCounter = 0;
	
	public void StartAuction(int round){
		endOfAuction = false;
		if(round!=0)
			setPlayerQueue(playersInRound); //set player queue with players in round
		else
			setInitialPlayerQueue(playersInRound);

			previousPlayer = playerQueue.get(0);
			//ListIterator<Player> it = playerQueue.listIterator();
		while(endOfAuction!=true){ //while everyone makes his move and all player's bets are equal
			//if(it.hasNext()){
			for(Player player : playerQueue){
				//Player player = it.next();
				//setPreviousPlayer(it.previous());
				currentPlayer = player;
				if(auctionCounter > 0 && checkIfBetsAreEqual(playerQueue) == true){	//if everyone took his turn and all player's bets are equal
					endOfAuction=true;
					break;
				}
				
				//TODO: add big and small blind to the pot, etc 
				
				MoveRestrictions.ResetRestrictions(getCurrentPlayer().getTa());
				MoveRestrictions.Restrict(this); //TODO: implement that class
				while(player.getName()==null){
					//player.setName(player.getMovement()); //get movement from server 
				}
				MoveRestrictions.RestrictAll(getCurrentPlayer().getTa());
				
		        switch(player.getName()){
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
					setCurrentBet(player.getCurrentBet());
					setCurrentPot(player.getCurrentBet());
				}
				if(player.playerState == ActionTaken.CALLING){
					setCurrentPot(getCurrentPot() + player.getCurrentBet());
				}				
				if(player.playerState == ActionTaken.RISING){ 
					setCurrentBet(player.getCurrentBet());
					setCurrentPot(getCurrentPot() + player.getCurrentBet()); 
				}
				if(player.playerState == ActionTaken.FOLDING){ 
					playersInRound.remove(player); //if player is folding, remove him from this round and queue
					//it.remove();
				}
				if(player.playerState == ActionTaken.ALLIN){ 
					setCurrentPot(getCurrentPot() + player.getCurrentBet());
					if(player.getCurrentBet() > getCurrentPot())
						setCurrentBet(player.getCurrentBet());
					player.setPlayerTokens(0);
					//it.remove();
				}
				player.setName(null);
				movesCounter++;
				previousPlayer = player;
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

	public Player getPreviousPlayer() {
		return previousPlayer;
	}

	public void setPreviousPlayer(Player previousPlayer) {
		this.previousPlayer = previousPlayer;
	}

}
