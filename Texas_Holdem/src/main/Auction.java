package main;

import java.util.List;

public class Auction {
	private List<Player> playerQueue;
	private List<Player> playersInRound;
	private int roundNumber;
	
	private int currentPot;
	private int currentBet;
	
	public Auction(int pot){
		setCurrentPot(0);
		setRoundNumber(0);
		setInitialPlayerQueue(playersInRound);
	}

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
	
	public void setInitialPlayerQueue(List<Player> players){
		int firstAddedPlayer = 0;
		for ( Player player : players){
			if(player.isBigBlind()){
				firstAddedPlayer = player.getPlayerIndex();
			}
			else if(playerQueue.isEmpty()==false){
				playerQueue.add(player);
			}
		}
		for(int i=0;i<=firstAddedPlayer;i++){
			playerQueue.add(players.get(i));
		}
	}
	
	public void setPlayerQueue(List<Player> players){
		int firstAddedPlayer = 0;
		for ( Player player : players){
			if(player.isSmallBlind()){
				playerQueue.add(player);
				firstAddedPlayer = player.getPlayerIndex();
			}
			else if(playerQueue.isEmpty()==false){
				playerQueue.add(player);
			}
		}
		for(int i=0;i<firstAddedPlayer;i++){
			playerQueue.add(players.get(i));
		}
	}
	
	private boolean checkIfBetsAreEqual(List<Player> players){
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
	
	private boolean endOfAuction = false;
	private int auctionCounter = 0;
	
	public void StartAuction(int round){
		endOfAuction = false;
		if(round!=0)
			setPlayerQueue(playersInRound); //set player queue with from players in round
			
		while(true){ //while everyone makes his move and all player's bets are equal
			for(Player player : playerQueue){
				if(auctionCounter > 0 && checkIfBetsAreEqual(playerQueue) == true){	//if everyone took his turn and all player's bets are equal
					endOfAuction=true;
					break;
				}
				
				player.getMovement(); //get movement from server 
				if(player.playerState == ActionTaken.ALLIN) //if player did all in action, remove him from queue
					playerQueue.remove(player);
				
				if(player.playerState == ActionTaken.FOLDING){
					playersInRound.remove(player); //if player is folding, remove him from this round
				}
				auctionCounter++;
			}
			if(endOfAuction == true)
			break;	
		}
		
		setPlayerQueue(null);
	}
	
}
