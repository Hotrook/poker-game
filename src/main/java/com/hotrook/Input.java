package com.hotrook;

public final class Input {
	private int numberOfPlayers;
	private int numberOfBots;
	private int bigBlindValue;
	private int smallBlindValue;
	private int initialTokens;
	//1 - no-limit, 2 - pot-limit, 3 - fixed-limit
	private GameType gameType;
	private int limit;
	
	public Input(){
		
	}

////GETTERS AND SETTERS////
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	public int getAllPlayers(){
		return numberOfPlayers + numberOfBots;
	}

	public void setNumberOfPlayers(int amoutOfPlayers) {
		this.numberOfPlayers = amoutOfPlayers;
	}

	public int getNumberOfBots() {
		return numberOfBots;
	}

	public void setNumberOfBots(int amountOfBots) {
		this.numberOfBots = amountOfBots;
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

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
}
