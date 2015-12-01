package main;

import java.util.List;

public class Auction {
	private List<Player> playerQueue;
	private List<Player> playersInGame;
	private int roundNumber;
	
	private int currentPot;
	private int currentBet;
	
	public Auction(int pot){
		setCurrentPot(0);
		setRoundNumber(0);
		setInitialPlayerQueue(playersInGame);
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
	
	public List<Player> getPlayersInGame() {
		return playersInGame;
	}

	public void setPlayersInGame(List<Player> playersInGame) {
		this.playersInGame = playersInGame;
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
	
	public void StartAuction(int round){
		if(round!=0)
			setPlayerQueue(playersInGame);
		
		
		
		
		setPlayerQueue(null);
	}
	
}
