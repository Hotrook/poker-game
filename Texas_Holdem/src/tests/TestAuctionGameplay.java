package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

import main.Auction;
import main.Player;

public class TestAuctionGameplay {

	@Test
	public void testEqualBets(){
		Player player1 = new Player("Jacek", 1000, 0);
		Player player2 = new Player("Wacek", 1000, 1);
		Player player3 = new Player("Placek", 1000, 2);
		Player player4 = new Player("Gacek", 1000, 3);
		List<Player> players = new ArrayList<Player>();
		player1.setDealerButton(true);
		player2.setBigBlind(true);
		player3.setSmallBlind(true);
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		Auction auction = new Auction(players);
		auction.getPlayerQueue().get(0).Bet(20);
		auction.getPlayerQueue().get(1).Bet(20);
		auction.getPlayerQueue().get(2).Bet(20);
		auction.getPlayerQueue().get(3).Bet(20);
		assertEquals(true,auction.checkIfBetsAreEqual(players));
	}
	
}
