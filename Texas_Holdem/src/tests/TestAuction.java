package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import main.Auction;
import main.AuctionGUI;
import main.Player;

public class TestAuction {
	private List<Player> players;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private AuctionGUI gui1;


	@Before
	public void setUp(){
		//create gui for players constructors
		
		
		//create new list of players
		players = new ArrayList<Player>();
		
		//gui will not be used in these tests
		//create 4 players 
		player1 = new Player("A", 1000, 0);
		player2 = new Player("B", 1000, 1);
		player3 = new Player("C", 1000, 2);
		player4 = new Player("D", 1000, 3);
		
		//add BB, SB, and DB
		player2.setDealerButton(true);
		player3.setSmallBlind(true);
		player4.setBigBlind(true);
		
		//add players to the players list
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
	}
	
	
	@Test
	public void testConstructor(){
		Auction auction = new Auction(players);
		assertEquals(players, auction.getPlayersInRound());
		assertEquals(0, auction.getPlayerQueue().size());
		assertEquals(0, auction.getRoundNumber());
		assertEquals(0, auction.getCurrentPot());
	}
	
	
	@Test
	public void testSetInitialPlayerQueue(){
		Auction auction = new Auction(players);
		auction.setInitialPlayerQueue(auction.getPlayersInRound());
		
		//first assertion: players amount in queue is equal to players amount in round
		assertEquals(auction.getPlayersInRound().size(), auction.getPlayerQueue().size());
		
		//second assertion: players are in right order
		//normal order: A, B - Dealer Button, C - Small Blind, D - Big Blind
		//right order starts with the first player after Big Blind
		//right order: A -> B -> C -> D
		assertEquals("A", auction.getPlayerQueue().get(0).getPlayerName());
		assertEquals("B", auction.getPlayerQueue().get(1).getPlayerName());
		assertEquals("C", auction.getPlayerQueue().get(2).getPlayerName());
		assertEquals("D", auction.getPlayerQueue().get(3).getPlayerName());
	}
	
	
	@Test
	public void testSetPlayerQueue(){
		Auction auction = new Auction(players);
		auction.setPlayerQueue(auction.getPlayersInRound());
	
		//first assertion: players amount in queue is equal to players amount in round
		assertEquals(auction.getPlayersInRound().size(), auction.getPlayerQueue().size());
		
		//second assertion: players are in right order
		//normal order: A, B - Dealer Button, C - Small Blind, D - Big Blind
		//right order starts with the Small Blind
		//right order: C -> D -> A -> B
		assertEquals("C", auction.getPlayerQueue().get(0).getPlayerName());
		assertEquals("D", auction.getPlayerQueue().get(1).getPlayerName());
		assertEquals("A", auction.getPlayerQueue().get(2).getPlayerName());
		assertEquals("B", auction.getPlayerQueue().get(3).getPlayerName());	
	}
	
	
	@Test
	public void testCheckIfBetsAreEqual(){
		Auction auction = new Auction(players);
		
		//initial assertion, each player has bet equal to 0
		assertEquals(true, auction.checkIfBetsAreEqual(players));
		
		//example situation of betting 
		//SCENARIO:
		//1. player1 bets 50				|A - 50, B - 0, C - 0, D - 0
		//2. player2 calls player1's bet	|A - 50, B - 50, C - 0, D - 0
		//3. player3 raises player2's bet	|A - 50, B - 50, C - 80, D - 0
		//4. player4 calls player3's bet	|A - 50, B - 50, C - 80, D - 80
		//5. player1 calls player4's bet	|A - 80, B - 50, C - 80, D - 80
		//6. player2 calls player1's bet	|A - 80, B - 80, C - 80, D - 80
		//bets should be equal
		player1.Bet(50);
		player2.Call(player1.getCurrentBet());
		player3.Raise(player2.getCurrentBet(),30);
		player4.Call(player3.getCurrentBet());
		assertEquals(false, auction.checkIfBetsAreEqual(players));
		player1.Call(player4.getCurrentBet());
		player2.Call(player1.getCurrentBet());
		assertEquals(true, auction.checkIfBetsAreEqual(players));
	}
	
	
	@Test
	public void testStartAuction(){
		//create mocked players
//		player1 = mock(Player.class);
//		player1.makePlayer("A", 1000, 0, gui1);
//		player2 = mock(Player.class);
//		player2.makePlayer("B", 1000, 1, gui1);
//		player3 = mock(Player.class);
//		player3.makePlayer("C", 1000, 2, gui1);
//		player4 = mock(Player.class);
//		player4.makePlayer("D", 1000, 3, gui1);
//		
//		//remove players from list and add mocked players
//		players = new ArrayList<Player>();
//		players.add(player1);
//		players.add(player2);
//		players.add(player3);
//		players.add(player4);
//		
//		Auction auction = new Auction(players);
		//This is the part where StartAuction should be tested
// 		Mockito.when(p1s.getMovement()).thenReturn("bet", "call");
//		Mockito.when(p2s.getMovement()).thenReturn("call", "call");
//		Mockito.when(p3s.getMovement()).thenReturn("raise");
//		Mockito.when(p4s.getMovement()).thenReturn("call");
		
		//auction.StartAuction(0);
		Auction auction = new Auction(players);
		Auction spy = spy(auction);
		doReturn(false).when(spy).checkIfBetsAreEqual(players);
		//assertEquals(false, auction.checkIfBetsAreEqual(players));
		
		//assertEquals(true, auction.checkIfBetsAreEqual(players));
		
	}
}
