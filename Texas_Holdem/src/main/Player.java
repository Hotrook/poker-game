package main;

import java.util.List;

public class Player{

	private final String playerName;
	private List<Card> hand;
	private List<Card> winningHand;
	private List<Card> drawCards;
	private boolean isInGame;
	private int playerTokens; //possibly List<Token>, where Token has it's value and amount
	private boolean isSmallBlind;
	private boolean isBigBlind;
	private boolean isDealerButton;
	private int currentBet;
	private final int playerIndex;
	
	public Player(String name, int tokens, int index){
		this.playerName=name;
		this.playerIndex=index;
		this.setPlayerTokens(tokens);
		setInGame(true);
		setHand(null);
		setWinningHand(null);
		setDrawCards(null);
		setCurrentBet(0);
		setBigBlind(false);
		setSmallBlind(false);
		setDealerButton(false);
	}

	//Getters and setters...
	
	public String getPlayerName() {
		return playerName;
	}

	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	public List<Card> getWinningHand() {
		return winningHand;
	}

	public void setWinningHand(List<Card> winningHand) {
		this.winningHand = winningHand;
	}

	public List<Card> getDrawCards() {
		return drawCards;
	}

	public void setDrawCards(List<Card> drawCards) {
		this.drawCards = drawCards;
	}

	public int getPlayerTokens() {
		return playerTokens;
	}

	public void setPlayerTokens(int playerTokens) {
		this.playerTokens = playerTokens;
	}

	public int getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}

	public boolean isSmallBlind() {
		return isSmallBlind;
	}

	public void setSmallBlind(boolean isSmallBlind) {
		this.isSmallBlind = isSmallBlind;
	}

	public boolean isBigBlind() {
		return isBigBlind;
	}

	public void setBigBlind(boolean isBigBlind) {
		this.isBigBlind = isBigBlind;
	}

	public boolean isDealerButton() {
		return isDealerButton;
	}

	public void setDealerButton(boolean isDealerButton) {
		this.isDealerButton = isDealerButton;
	}

	public boolean isInGame() {
		return isInGame;
	}

	public void setInGame(boolean isInGame) {
		this.isInGame = isInGame;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}

	// Auction methods
	public ActionTaken playerState;
		
	public void Check(){
		playerState = ActionTaken.CHECKING;
	}
	
	public void Bet(int betValue){
		setPlayerTokens(getPlayerTokens() - betValue);
		setCurrentBet(getCurrentBet() + betValue);
		playerState = ActionTaken.BETING;
	}

	public int Raise(int betValue, int riseValue){
		setPlayerTokens(getPlayerTokens() - betValue - riseValue);
		setCurrentBet(getCurrentBet() + betValue + riseValue);
		playerState = ActionTaken.RISING;
		return riseValue;
	}

	public void Fold(){
		playerState = ActionTaken.FOLDING;
	}
	
	public int AllIn(){
		playerState = ActionTaken.ALLIN;
		setCurrentBet(getCurrentBet() + playerTokens);
		setPlayerTokens(0);
		return this.playerTokens;
	}

	/**
	 * based on button pressed, calls one of auction methods
	 * used in auction to wait for players action
	 */
	public void getMovement() {
		// TODO Auto-generated method stub
	}
}
