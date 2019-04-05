package com.hotrook;

import static org.junit.Assert.*;

import com.hotrook.exceptions.InvalidNumberOfRankException;
import com.hotrook.exceptions.InvalidNumberOfSuitException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGame {
	

	Game game;
	List <Player> players;
	Player player;
	Player player1;
	Player player2;
	Player player3;
	

	@Before
	public void init  () throws InvalidNumberOfRankException,
			InvalidNumberOfSuitException,
							    NoSuchFieldException, 
							    SecurityException{
		players = new ArrayList<Player>();
		
		initializePlayers();
		player3.setInGame(false);
		addPlayers();
		
		game = new Game (players);
		
	
		
	}



	@Test
	public void testDistributeCards() throws InvalidNumberOfRankException, 
											 InvalidNumberOfSuitException {
		
		game.distributeCards();
		
		assertEquals(2, game.getPlayers().get(0).getHand().size());
	}
	
	@Test 
	public void testMakePlayersInGame(){
		game.makePlayersInGame();
		
		for( int i = 0 ; i < game.getPlayers().size() ; ++i )
		assertEquals(true, game.getPlayers().get(i).isInGame());
	}
	
	
	
	
	@Test 
	public void testPutCardsOnTheTable() throws InvalidNumberOfRankException,
												InvalidNumberOfSuitException{
		
		game.putCardsOnTheTable(0);
		Assert.assertEquals(3, game.getTableCards().size() );
		
		game.putCardsOnTheTable(1);
		Assert.assertEquals(4, game.getTableCards().size() );
	}
	
	
	
	
	
	
	@Test
	public void testCreateHelpingList(){
		List<Player> expected = new ArrayList<Player>();
		List<Player> created = new ArrayList<Player>();
		expected.add(player);
		expected.add(player1);
		expected.add(player2);
		
		created = game.createHelpingList( 253.100806, game.getPlayers() );
		
		Assert.assertEquals( expected,created);
		
	}
	
	
	
	
	@Test
	public void testGiveGainToWinners() throws InvalidNumberOfRankException,
											   InvalidNumberOfSuitException{
		
		Auction auction = new Auction(players);
		
		auction.setCurrentPot(50+70+120);
		player.setCurrentTotalBet(50);
		player1.setCurrentTotalBet(70);
		player2.setCurrentTotalBet(120);
		
		initializeCards();
		game.auction = auction;
		
		List<Player> winnersList = new ArrayList<Player>();
		
		winnersList = game.createSortedWinnersList();
		
		
		game.giveGainToWinners(winnersList);
		
		Assert.assertEquals(81, player.getPlayerTokens());
		Assert.assertEquals(121, player1.getPlayerTokens());
		Assert.assertEquals(56, player2.getPlayerTokens());
		
	}
	
	
	
	
	@Test
	public void testChangeSmallBlind(){
		
		game.changeSmallBlind();
		
		Assert.assertEquals(true,player3.isSmallBlind());
		Assert.assertEquals(false,player3.isBigBlind());
		Assert.assertEquals(true,player.isBigBlind());
		Assert.assertEquals(false,player2.isSmallBlind());
		
	}
	
	
	
	

	
	
	
	@Test
	public void testRemoveLosers(){
		
		List<Player> expected = new ArrayList<Player>();
		player1.setPlayerTokens(0);
		player3.setPlayerTokens(0);
		
		expected.add(player);
		expected.add(player2);
		
		game.removeLosers();
		
		Assert.assertEquals(expected, game.getPlayers());
	}
											

	
	
	
	
	private void initializePlayers() throws InvalidNumberOfRankException,
											InvalidNumberOfSuitException{

		List<Card> playerCards = new ArrayList<Card>();
		List<Card> playerCards1 = new ArrayList<Card>();
		List<Card> playerCards2 = new ArrayList<Card>();
		List<Card> playerCards3 = new ArrayList<Card>();
		
		player = new Player("player0", 6, 0);
		player.setPower(253.100806);
		player.setCurrentTotalBet(60);
		
		player1 = new Player("player1", 6, 1);
		player1.setPower(253.100806);
		player1.setCurrentTotalBet(60);
		
		player2 = new Player("player2", 6, 2);
		player2.setPower( 253.100806 );
		
		player3 = new Player("player3", 6, 3);
		
		playerCards.add( new Card(2,1));
		playerCards.add( new Card(3,1));
		playerCards1.add( new Card(2,0));
		playerCards1.add(new Card(3,2));
		playerCards2.add(new Card(11,2));
		playerCards2.add(new Card(12,2));
		playerCards3.add(new Card(6,1));
		playerCards3.add(new Card(6,2));
		
		player.setHand( playerCards );
		player1.setHand( playerCards1 );
		player2.setHand( playerCards2 );
		player3.setHand( playerCards3 );
		
		player2.setSmallBlind(true);
		player3.setBigBlind(true);
		
	
	}
	
	private void addPlayers(){
		players.add(player);
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
	}
	
	
	
	private void initializeCards() throws InvalidNumberOfRankException, 
										  InvalidNumberOfSuitException {
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add( new Card( 2,0) );
		tableCards.add( new Card( 4,0) );
		tableCards.add( new Card( 6,0) );
		tableCards.add( new Card( 8,1) );
		tableCards.add( new Card( 10,2) );
		
		game.setTableCards(tableCards);
		
	}
}
