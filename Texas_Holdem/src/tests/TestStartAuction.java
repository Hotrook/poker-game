package tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.Auction;
import main.AuctionGUI;
import main.Player;


public class TestStartAuction {
	private List<Player> players;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private AuctionGUI gui1;
	private AuctionGUI gui2;
	private AuctionGUI gui3;
	private AuctionGUI gui4;

	@Test
	public void TestStartAuctionGameplay(){
		gui1 = new AuctionGUI();
		gui2 = new AuctionGUI();
		gui3 = new AuctionGUI();
		gui4 = new AuctionGUI();
		
		//create new list of players
		players = new ArrayList<Player>();
		
		//gui will not be used in these tests
		//create 4 players 
		player1 = new Player("A", 1000, 0, gui1);
		player2 = new Player("B", 1000, 1, gui2);
		player3 = new Player("C", 1000, 2, gui3);
		player4 = new Player("D", 1000, 3, gui4);
		
		//add BB, SB, and DB
		player2.setDealerButton(true);
		player3.setSmallBlind(true);
		player4.setBigBlind(true);
		
		//add players to the players list
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		gui1.setVisible(true);
		gui2.setVisible(true);
		gui3.setVisible(true);
		gui4.setVisible(true);
		
		Auction auction = new Auction(players);
		gui1.globalAuction = auction;
		gui2.globalAuction = auction;
		gui3.globalAuction = auction;
		gui4.globalAuction = auction;
		auction.startAuction(0);
		
		
	}
}
