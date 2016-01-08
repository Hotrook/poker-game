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
	private int raiseValue;
	private int difference;
	
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
	
	
	
	public void placeBlindsOnTable(){
		int bbvalue = Server.input.getBigBlindValue();
		int sbvalue = Server.input.getSmallBlindValue();
		
		
		//placing blinds on table
		for(Player pl: playerQueue){
			if(pl.isBigBlind() == true){
				setCurrentPot(getCurrentPot() + bbvalue); 
				setCurrentBet(bbvalue);
				pl.setCurrentBet(bbvalue);
				pl.setCurrentTotalBet(pl.getCurrentTotalBet() + bbvalue);
				pl.playerState = ActionTaken.BETING;
				pl.setPlayerTokens(pl.getPlayerTokens() - bbvalue);
			}
			if(pl.isSmallBlind() == true){
				setCurrentPot(getCurrentPot() + sbvalue); 
				pl.setCurrentBet(sbvalue);
				pl.setCurrentTotalBet(pl.getCurrentTotalBet() + sbvalue);
				//pl.playerState = ActionTaken.BETING;
				pl.setPlayerTokens(pl.getPlayerTokens() - sbvalue);
			}	
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
		if(getCurrentPlayer().isBigBlind() == true && getRoundNumber() == 0 && getCurrentBet() > getCurrentPlayer().getCurrentBet())
			data += ";" + "BETING";
		else if(getCurrentPlayer().isBigBlind() == true && getRoundNumber() == 0 && getCurrentBet() <= getCurrentPlayer().getCurrentBet())
			data += ";" + "BB";
		else 
			data += ";" + PP_action;

		data += ";" + movesCounter;
		data += ";" + getCurrentBet();
		data += ";" + getCurrentPot();
		
		for(Player pl : players){
			data += ";" + pl.getPlayerName();
			if(pl.isBigBlind())
				data += ";" + pl.getPlayerTokens() + " (BB)";
			else if(pl.isSmallBlind())
				data += ";" + pl.getPlayerTokens() + " (SB)";
			else
				data += ";" + pl.getPlayerTokens(); 
		}
		return data;
	}
////ACTUAL METHOD FOR STARTING AUCTION////	
	private boolean endOfAuction = false;
	private Player currentPlayer = null;
	private Player previousPlayer = null;
	public int movesCounter = 0;
	private ActionTaken PP_action = null;
	
	public void startAuction(int round){
		setRoundNumber(round);
		endOfAuction = false;
		if(round!=0)
			setPlayerQueue(playersInRound); //set player queue with players in round
		else
			setInitialPlayerQueue(playersInRound);

			
		if(round == 0)
			placeBlindsOnTable();
			
			ListIterator<Player> it;// = playerQueue.listIterator();
		while(endOfAuction!=true){ //while everyone makes his move and all player's bets are equal
			it = playerQueue.listIterator();
			while(it.hasNext()){
			//for(Player player : playerQueue){
				
				
				Player player = it.next();
				currentPlayer = player;
				
				
				//set previous player
				if(currentPlayer.getPlayerIndex() == 0)
					previousPlayer = playerQueue.get(playerQueue.size()-1);
				else
					previousPlayer = playerQueue.get(currentPlayer.getPlayerIndex() -1);
				
				PP_action = previousPlayer.playerState;
				
				//send data seen on table to each player in game 
				sendDataToEachClient(playerQueue);
				
				//send hand info to each player
				sendHandInfoToEachClient(playerQueue);
				
				//activate current player
				currentPlayer.setActive(createDataPackage(playerQueue));
				
				
				difference = getCurrentBet() - player.getCurrentBet();
				
				switch(player.getActionName()){
		        case "check": player.Check(); break;
		        case "call": player.Call(difference); break;
		        case "bet": player.Bet(player.getCurrentBet()); break; 
		        case "raise": player.Raise(player.getCurrentBet() + getCurrentBet()); break;
		        case "fold": player.Fold(); break;
		        case "allin": player.AllIn(); break;
		        default: break;
		        }
				//wykaszam getcurrenttotalbet >> getcurrentbet
				
				if(player.playerState == ActionTaken.CHECKING){ 
					//do nothing important
				}			
				if(player.playerState == ActionTaken.BETING){
					setCurrentBet(player.getCurrentBet());
					setCurrentPot(getCurrentPot() + player.getCurrentBet());
				}
				if(player.playerState == ActionTaken.CALLING){
					setCurrentPot(getCurrentPot() + difference);
				}				
				if(player.playerState == ActionTaken.RISING){ 
					//setRaiseValue(player.getCurrentBet());
					setCurrentBet(player.getCurrentBet());
					setCurrentPot(getCurrentPot() + player.getCurrentBet()); 
				}
				if(player.playerState == ActionTaken.FOLDING){ 
					playersInRound.remove(player); //if player is folding, remove him from this round and queue
					playerQueue.remove(player);
				}
				if(player.playerState == ActionTaken.ALLIN){ 
					setCurrentPot(getCurrentPot() + player.getCurrentBet());
					if(player.getCurrentBet() > getCurrentBet())
						setCurrentBet(player.getCurrentBet());
					player.setPlayerTokens(0);
					playerQueue.remove(player);
				}
				
				
				//moved to the beginning of auction
				//sendDataToEachClient(playerQueue);
				currentPlayer.setBlocked();
				movesCounter++;
				//getCurrentPlayer().playerState = null;
				//previousPlayer = currentPlayer;
				getCurrentPlayer().setActionName(null);

				for(Player pl : playerQueue)
					System.out.print(pl.getCurrentBet() + " ");
				System.out.println();
				if(movesCounter >= playerQueue.size()  && checkIfBetsAreEqual(playerQueue) == true){	//if everyone took his turn and all player's bets are equal
					endOfAuction=true;
					break;
				}
				if(playerQueue.size() <= 1){
					endOfAuction = true;
					break;
				}
			}
		}
		if(endOfAuction==true)
			System.out.println("Koniec aukcji!");
		movesCounter = 0;
		PP_action = null;
		//reseting values before next auction
		//*current bet
		setCurrentBet(0);
		//*player state and his bet for each player left in round
		for(Player player : playerQueue){
			player.playerState = null;
			player.setCurrentBet(0);
		}
		//*player queue 
		playerQueue = new ArrayList<Player>();
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

	public int getRaiseValue() {
		return raiseValue;
	}

	public void setRaiseValue(int raiseValue) {
		this.raiseValue = raiseValue;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
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
