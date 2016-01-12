package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Player{

	protected String playerName;
	protected List<Card> hand;
	protected List<Card> winningHand;
	protected List<Card> drawCards;
	protected boolean isInGame;
	protected int playerTokens; //possibly List<Token>, where Token has it's value and amount
	protected boolean isSmallBlind;
	protected boolean isBigBlind;
	protected boolean isDealerButton;
	protected boolean isBot;
	protected int currentTotalBet;
	protected int currentAuctionBet;
	protected int currentBet;
	protected int currentPlayerBet;
	protected int currentPot;
	protected final int playerIndex;
	protected boolean stateChanged;
	protected String actionName;
	protected GameType gameType;
	protected int limit ;
	protected double power;
	public static BufferedReader in;
	public static PrintWriter out;

	public Player(Socket socket, int tokens, int index, GameType gameType, boolean isBot){
		this.playerName=actionName;
		this.playerIndex=index;
		this.setPlayerTokens(tokens);
		setBot(isBot);
		setGameType(gameType);
		setInGame(true);
		setHand(null);
		setWinningHand(null);
		setDrawCards(null);
		setCurrentTotalBet(0);
		setCurrentBet(0);
		setBigBlind(false);
		setSmallBlind(false);
		setDealerButton(false);
		setStateChanged(false);
		setActionName(null);
		if( isBot() == false ){
	        try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(),true);
				Server.writers.add(out);
				Server.readers.add(in);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public Player(String name, int tokens, int index){
		this.playerName=name;
		this.playerIndex=index;
		this.setPlayerTokens(tokens);
		setGameType(gameType);
		setInGame(true);
		setHand(null);
		setWinningHand(null);
		setDrawCards(null);
		setCurrentTotalBet(0);
		setCurrentBet(0);
		setBigBlind(false);
		setSmallBlind(false);
		setDealerButton(false);
		setStateChanged(false);
		setActionName(null);
	}
	
	
	public void sendHandInfoToClient(){
		String handInfo = null;
		handInfo = "hand";
		for(Card card : getHand()){
			handInfo += ";" + card.getRank() + ";" + card.getSuit();
		}
		
		Server.writers.get(getPlayerIndex()).println(handInfo);
	}
	
	public void sendDataToEachClient(String dataPackage){
		System.out.println(getPlayerName() + " " + dataPackage);
		Server.writers.get(getPlayerIndex()).println(dataPackage);
	}
	
	
	public void getMovement(){
		String[] action;
		try {
			while(true){
				action = Server.readers.get(playerIndex).readLine().split(";");
				if(action!=null){
					setActionName(action[0]);
					System.out.print(getPlayerName() + " " +  action[0]);
					
					if(action.length > 1){
						setCurrentBet(Integer.parseInt(action[1]));
						System.out.println(" " + action[1]);
					}
					break;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
////AUCTION METHODS////
	
	public ActionTaken playerState;
		
	public void Check(){
		playerState = ActionTaken.CHECKING;
	}
	
	public void Bet(int betValue){
		setPlayerTokens(getPlayerTokens() - betValue);
		setCurrentTotalBet(getCurrentTotalBet() + betValue);
		setCurrentBet(betValue);
		playerState = ActionTaken.BETING;
	}
	
	public void Call(int difference){
		setCurrentTotalBet(getCurrentTotalBet() + difference);
		setPlayerTokens(getPlayerTokens() - difference);
		setCurrentBet(getCurrentBet() + difference);
		playerState = ActionTaken.CALLING;
	}

	public void Raise(int auctionBetValue){
		setPlayerTokens(getPlayerTokens() - (auctionBetValue - getCurrentPlayerBet()));
		setCurrentTotalBet(getCurrentTotalBet() + (auctionBetValue - getCurrentPlayerBet()));
		setCurrentBet(auctionBetValue);
		//setCurrentPlayerBet(auctionBetValue);
		playerState = ActionTaken.RISING;
	}

	public void Fold(){
		playerState = ActionTaken.FOLDING;
	}
	
	public int AllIn(){
		playerState = ActionTaken.ALLIN;
		setCurrentTotalBet(getCurrentTotalBet() + getPlayerTokens());
		setCurrentBet(getPlayerTokens());
		setPlayerTokens(0);
		return this.playerTokens;
	}
	
	/**
	 * based on button pressed, calls one of auction methods
	 * used in auction to wait for players action
	 */

////GETTERS AND SETTERS////

	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String name){
		this.playerName = name;
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

	public int getCurrentTotalBet() {
		return currentTotalBet;
	}

	public void setCurrentTotalBet(int currentBet) {
		this.currentTotalBet = currentBet;
		// TODO : SET CURRENT BET IN GUI AS WEll
	}

	public int getCurrentAuctionBet() {
		return currentAuctionBet;
	}

	public void setCurrentAuctionBet(int currentAuctionBet) {
		this.currentAuctionBet = currentAuctionBet;
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

	public boolean isStateChanged() {
		return stateChanged;
	}

	public void setStateChanged(boolean stateChanged) {
		this.stateChanged = stateChanged;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String name) {
		this.actionName = name;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public void setBlocked() {
		setCurrentPlayerBet(getCurrentBet());
		Server.writers.get(getPlayerIndex()).println("set blocked");
	}

	public void setActive(String data, int round) {
		if( (isBigBlind() == false && isSmallBlind() == false) || round > 0 )
			setCurrentPlayerBet(0);
		Server.writers.get(getPlayerIndex()).println("set active;" + data);
		getMovement();
	}

	public boolean isBot() {
		return isBot;
	}

	public void setBot(boolean isBot) {
		this.isBot = isBot;
	}

	public void setTableCards(List<Card> tableCards) {}
	
	public void setLimit(int limit ){
		this.limit = limit;
	}
	
	public int getLimit(){
		return limit;
	}

	public void setCurrentPot(int currentPot2) {
		this.currentPot = currentPot2;
	}
	
	public int getCurrentPot(){
		return currentPot;
	}
	
	public void setCurrentPlayerBet(int value){
		this.currentPlayerBet = value;
	}
	
	public int getCurrentPlayerBet(){
		return currentPlayerBet;
	}
	
	
}
