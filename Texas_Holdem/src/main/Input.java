package main;

public final class Input {
	private int amoutOfPlayers;
	private int amountOfBots;
	private int bigBlindValue;
	private int smallBlindValue;
	private int initialTokens;
	//1 - no-limit, 2 - pot-limit, 3 - fixed-limit
	private int gameType;
	
	private Input(){
		
	}

////GETTERS AND SETTERS////
	
	public int getAmoutOfPlayers() {
		return amoutOfPlayers;
	}

	public void setAmoutOfPlayers(int amoutOfPlayers) {
		this.amoutOfPlayers = amoutOfPlayers;
	}

	public int getAmountOfBots() {
		return amountOfBots;
	}

	public void setAmountOfBots(int amountOfBots) {
		this.amountOfBots = amountOfBots;
	}

	public int getBigBlindValue() {
		return bigBlindValue;
	}

	public void setBigBlindValue(int bigBlindValue) {
		this.bigBlindValue = bigBlindValue;
	}

	public int getSmallBlindValue() {
		return smallBlindValue;
	}

	public void setSmallBlindValue(int smallBlindValue) {
		this.smallBlindValue = smallBlindValue;
	}

	public int getInitialTokens() {
		return initialTokens;
	}

	public void setInitialTokens(int initialTokens) {
		this.initialTokens = initialTokens;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}
	
	
}
