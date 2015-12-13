package tests;

import java.util.ArrayList;
import java.util.List;

import main.Auction;
import main.AuctionGUI;
import main.Player;

public class TestAuctionGUI {

	public static void main(String[] args) {
		//create GUI//
		AuctionGUI gui1 = new AuctionGUI();
		//AuctionGUI gui2 = new AuctionGUI();
		//AuctionGUI gui3 = new AuctionGUI();
		//AuctionGUI gui4 = new AuctionGUI();
		//create players//
		Player player1 = new Player("A", 1000, 0, gui1);
		Player player2 = new Player("B", 1000, 1, gui1);
		Player player3 = new Player("C", 1000, 2, gui1);
		Player player4 = new Player("D", 1000, 3, gui1);
		List<Player> players = new ArrayList<Player>();
		player1.setBigBlind(true);
		player2.setSmallBlind(true);
		player3.setDealerButton(true);
		players.add(player4);
		players.add(player3);
		players.add(player2);
		players.add(player1);
		//create Auction//
		Auction auction = new Auction(players);
		//add auction to guis//
		gui1.globalAuction = auction;
		//gui2.globalAuction = auction;
		//gui3.globalAuction = auction;
		//gui4.globalAuction = auction;
		//show guis//
		gui1.setVisible(true);
		//gui2.setVisible(true);
		//gui3.setVisible(true);
		//gui4.setVisible(true);
		//start auction//
		auction.StartAuction(0);
	}

}
