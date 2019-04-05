package com.hotrook;

import com.hotrook.exceptions.InvalidNumberOfRankException;
import com.hotrook.exceptions.InvalidNumberOfSuitException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Seba
 *
 * TODO:
 * Get players from server
 * 
 */
public class Game {
	
	private List<Player> players;
	private List<Player> losers;
	private List<Card> tableCards = new ArrayList<Card>();
	public Auction auction;
	
	
	public Game(List<Player> players) throws InvalidNumberOfRankException,
            InvalidNumberOfSuitException {
		this.players = new ArrayList<Player>();
		this.players.addAll(players);
		this.losers = new ArrayList<Player>();
	}
	
	
	
	
	public void play() throws InvalidNumberOfRankException,
							  InvalidNumberOfSuitException{
		players.get(0).setSmallBlind(true);
		players.get(1).setBigBlind(true);
		
		for(Player player : players){
			if( player.isBot() == false ){
				if(Server.input.getGameType().equals(GameType.FIXLIMIT))
					Server.writers.get(player.getPlayerIndex()).println("game type;"
					+ Server.input.getGameType().toString() + ";" + Server.input.getLimit());
				else
					Server.writers.get(player.getPlayerIndex()).println("game type;" + Server.input.getGameType());
			}
			else{
				player.setGameType(Server.input.getGameType());
				if(Server.input.getGameType().equals(GameType.FIXLIMIT)){
					player.setLimit(Server.input.getLimit());
				}
			}
		}
		
		while( players.size() > 1 ){
			try {
        round();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
		}
		System.out.println(players.get(0).getPlayerTokens());
		endGame();
	}
	
	
	
	private void endGame(){
		for(Player player : auction.getPlayersInRound()){
		  if( !player.isBot())
			Server.writers.get(player.getPlayerIndex()).println("win");
		}
		//System.exit(0);
	}
	
	
	
	public void round() throws InvalidNumberOfRankException, 
							   InvalidNumberOfSuitException, InterruptedException{
		
		List<Player> winnersList = new ArrayList<Player> ();
	
		distributeCards();
		makePlayersInGame();
		
		auction = new Auction(players);
		System.out.println(" W grze jest " + players.size());
		
		for( int i = 0 ; i <= 3 ; ++i){
			auction.startAuction(i);
			putCardsOnTheTable(i);
			showCardsOnTableToEachPlayer(players);
			if( auction.getPlayersInRound().size() == 1 ){
				i = 4; // end of for
			}
			else{
				int counter = 0 ;
				for( int j = 0 ; j < players.size(); ++j){
					if( players.get(j).isInRound() == true ){
						counter++;
					}
				}
				
				if ( counter <= 1 ){
					i = 4; // end of for 
				}
			}
		}
		int suma = 0 ;
    for ( Player player : players ){
      suma += player.getPlayerTokens();
    }
    System.out.println( " kasa :  " + suma);
    suma += auction.getCurrentPot();
		 System.out.println("Control level -1");
		 winnersList = createSortedWinnersList();
		 System.out.println("Control level 0");
		 giveGainToWinners(winnersList);
		 System.out.println("Control level 1");
		 changeSmallBlind();
		 System.out.println("Control level 2");
		 removeLosers();
		 System.out.println("Control level 3");
		 informLosers();
		 System.out.println("Control level 4");
		 //clear the table
		 clearTableViewForEachPlayer(players);
		 System.out.println("Control level 5");
		 
		  suma = 0 ;
		 for ( Player player : players ){
		   suma += player.getPlayerTokens();
		 }
		 
		 suma += auction.getCurrentPot();
		 System.out.println( " KASSA :  "+  suma);
		 

		 
	}
	
	
	
	
	private void showCardsOnTableToEachPlayer(List<Player> players) {
		String cards;
		cards = "table cards";
		for(Card card : tableCards){
			cards += ";" + card.getRank() + ";" + card.getSuit();
		}
		
		for(Player player : players){
			if( player.isBot() == false){
				Server.writers.get(player.getPlayerIndex()).println(cards);
			}
			else{
				player.setTableCards(tableCards);
			}
		}
	}
	
	
	
	
	private void clearTableViewForEachPlayer(List<Player> players) {
		String cards;
		cards = "table cards";
		cards += "; " + "; ";
		cards += "; " + "; ";
		cards += "; " + "; ";
		cards += "; " + "; ";
		cards += "; " + "; ";
		
		for(Player player : players){
			if( player.isBot() == false )
			Server.writers.get(player.getPlayerIndex()).println(cards);
		}
	}




	public List<Player> getPlayers(){
		return players;
	}
	
	
	
	public List<Card> getTableCards(){
		return tableCards;
	}
	
	
	
	
	public void setTableCards(List<Card> tableCards){
		this.tableCards = tableCards;
	}
	
	
	
	public void changeSmallBlind() {
		int counter = 0;
		for(Player player : players){
		  if( player.isBigBlind() ){
		    player.setBigBlind(false);
		  }
		}
		while ( counter < players.size() && players.get(counter).isSmallBlind() == false){
			counter++;
		}
		
		players.get(counter).setSmallBlind(false);
		
		
		do{
			counter = (counter+1)%players.size();
		}while( players.get(counter).getPlayerTokens() == 0);
		
		players.get(counter).setSmallBlind(true);
		players.get(counter).setBigBlind(false);
		
		do{
			counter = (counter+1)%players.size();
		}while( players.get(counter).getPlayerTokens() == 0 && players.get(counter).isSmallBlind());
		players.get(counter).setBigBlind(true);
		
		
	}




	public void makePlayersInGame() {
		for( Player player : players){
			player.setInGame(true);
			player.setInRound(true);
			player.setActionName(null);
			player.playerState = null;
		}
	}




	public void giveGainToWinners(List<Player> winnersList) {
		int[] wagers = new int[players.size()+7];
		int lowerRate = 0;
		int higherRate = 0;
		int helpingPot = 0;
		double power = 0 ;
		List<Player> helpingList = new ArrayList<Player>();
		for( int i = 0 ; i < players.size() ; ++i ){
			wagers[i] = players.get(i).getCurrentTotalBet();
		}
		
		while( auction.getCurrentPot() > 0  ){


			System.out.println("w puli " +auction.getCurrentPot());
			power = winnersList.get(0).getPower(); 
			
			helpingList = createHelpingList(power, winnersList);
			
			
			higherRate = helpingList.get(0).getCurrentTotalBet(); 
			System.out.println("1Higher Rate: " + higherRate);
			while(helpingList.isEmpty() == false ){
				helpingPot = 0;
				higherRate = helpingList.get(0).getCurrentTotalBet(); 
				System.out.println("h - l : " +  higherRate + " " + lowerRate);
				
				for( int i = 0 ; i < players.size() ; ++i ){
					if( wagers[ i ] > lowerRate ){ 
						if( wagers[ i ] > higherRate ){
							helpingPot += (higherRate - lowerRate );
						}
						else{
							helpingPot += (wagers[ i ] - lowerRate);
						}
					}
				}
				
				auction.setCurrentPot((auction.getCurrentPot()-helpingPot));
				
				int tempHelp = helpingPot/helpingList.size();
				for( Player player : helpingList){
					System.out.println("gracz " + player.getPlayerName() + " " + player.getPlayerIndex()+"dostal " + helpingPot/helpingList.size());
					player.setPlayerTokens(player.getPlayerTokens()+tempHelp);
					helpingPot -= tempHelp;
				}
				if( helpingPot != 0){
				  helpingList.get(0).setPlayerTokens( helpingList.get(0).getPlayerTokens()+helpingPot);
				}
				
				while( helpingList.get(0).getCurrentTotalBet() == higherRate ){
					helpingList.remove(0);
					if( helpingList.size() == 0){
						break;
					}
				}
				
				lowerRate = higherRate;
				
			}
		}
	}




	public List<Player> createHelpingList(double power, List<Player> winnersList) {
		
		List <Player> helpingList = new ArrayList<Player>();
		
		for( Player player : winnersList ){
			if( player.getPower() == power ){
				helpingList.add(player);
			}
		}
		
		for( Player player : helpingList ){
			winnersList.remove(player);
		}
		
		return helpingList;
	}




	public List<Player> createSortedWinnersList() {
		List<Player> winners = new ArrayList<Player>();
		double max = 0;
		
		for( Player player : players){
			if( player.isInGame() ){
				HandDeterminer.determineHand(player.getHand(), tableCards, player); 
				winners.add(player);
			}
		}
		
		Collections.sort(winners, new SortPlayer() );
		List<Player> toRemove = new ArrayList<Player>();
	
		for( int i = 0 ; i < winners.size(); ++i )
		  for( int j = i -1  ; j >= 0 ; --j ){
		    if( winners.get(i).getCurrentTotalBet() < winners.get(j).getCurrentTotalBet())
		      toRemove.add(winners.get(i));
		    if( winners.get(i).getCurrentTotalBet() == winners.get(j).getCurrentTotalBet() &&
		        winners.get(i).getPower() < winners.get(j).getPower())
		      toRemove.add(winners.get(i));
		  }
		for ( Player player : winners )
      System.out.println("Wygrani: " +player.getPower() + " " + player.getCurrentTotalBet() );
		for( Player pl : toRemove){
		  winners.remove(pl);
		}
		for ( Player player : winners )
		  System.out.println("2Wygrani: " +player.getPower() + " " + player.getCurrentTotalBet() );
		return winners;
	}




	public void putCardsOnTheTable(int i) throws InvalidNumberOfRankException, 
										  InvalidNumberOfSuitException{
		if( i == 3){
			//ignore, this is the last round of auction, where all cards are already on table
		}
		else{
			if( i == 0 ){
				Deck.getInstance().initializeCards();
				tableCards.clear();
				tableCards.addAll( Deck.getInstance().giveCards( 3 ));
			}
			else{
				tableCards.addAll( Deck.getInstance().giveCards( 1 ));
			}
		}
	}

	
	
	
	public void distributeCards() throws InvalidNumberOfRankException, 
										  InvalidNumberOfSuitException {
		for( Player player : players){
			player.setHand( Deck.getInstance().giveCards(2) );
		}
	}
	
	
	

	public void removeLosers() {
		List <Player> toRemove = new ArrayList<Player>();
		for( Player player : players){
			if( player.getPlayerTokens() == 0 ){
				toRemove.add(player);
			}
		}
		for( Player player : toRemove){
			losers.add(player);
			players.remove(player);
		}
	}
	
	public void informLosers(){
		for( Player loser : losers){
		  if(!loser.isBot())
			  Server.writers.get(loser.getPlayerIndex()).println("lose");
		}
		losers.clear();
	}
	
	

	private static class SortPlayer implements Comparator<Player>{

		@Override
		public int compare(Player o1, Player o2) {
			if(o1.getPower() < o2.getPower()){
				return 1;
			}
			else if( o1.getPower() > o2.getPower() ){
				return -1;
			}
			else {
				if( o1.getCurrentTotalBet() > o2.getCurrentTotalBet() ){
					return 1;
				}
				else if( o1.getCurrentTotalBet() < o2.getCurrentTotalBet() ){
					return -1;
				}
				else
					return 0;
			}
		}
		
	}
	
	
	
	
	
	
	
}
