package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.ActionTaken;
import main.AuctionGUI;
import main.Player;

public class TestPlayer{
	
	@Test
	public void test_PlayerConstructor(){
		AuctionGUI ta = new AuctionGUI();
		Player player = new Player("Adam", 1000, 0,ta);
		assertEquals(player.getPlayerName(),"Adam");
		assertEquals(player.getPlayerTokens(),1000);
		assertEquals(player.getPlayerIndex(),0);
		assertEquals(player.isInGame(),true);
		assertEquals(player.isBigBlind(),false);
		assertEquals(player.isSmallBlind(),false);
		assertEquals(player.isDealerButton(),false);
	}
	
	@Test
	public void test_Check(){
		AuctionGUI ta = new AuctionGUI();
		Player player = new Player("Adam",1000,0,ta);
		player.Check();
		assertEquals(player.playerState,ActionTaken.CHECKING);
	}
	
	@Test
	public void test_Bet(){
		AuctionGUI ta = new AuctionGUI();
		Player player = new Player("Adam", 1000, 0,ta);
		player.Bet(400);
		assertEquals(player.getPlayerTokens(),600);
		assertEquals(player.getCurrentBet(),400);
		assertEquals(player.playerState,ActionTaken.BETING);
	}
	
	@Test
	public void test_Raise(){
		AuctionGUI ta = new AuctionGUI();
		Player player = new Player("Adam", 1000, 0,ta);
		Player player2 = new Player("Tomek", 1000, 1,ta);
		player.Bet(300);
		player2.Raise(player.getCurrentBet(),400);
		assertEquals(player2.getPlayerTokens(),300);
		assertEquals(player2.getCurrentBet(),700);
		assertEquals(player2.playerState,ActionTaken.RISING);
	}
	
	@Test
	public void test_Fold(){
		AuctionGUI ta = new AuctionGUI();
		Player player = new Player("Adam", 1000, 0,ta);
		player.Fold();
		assertEquals(player.playerState,ActionTaken.FOLDING);
	}
	
	@Test
	public void test_AllIn(){
		AuctionGUI ta = new AuctionGUI();
		Player player = new Player("Adam", 1000, 0,ta);
		player.AllIn();
		assertEquals(player.getPlayerTokens(),0);
		assertEquals(player.getCurrentBet(),1000);
		assertEquals(player.playerState,ActionTaken.ALLIN);		
	}
	
	
	
}
