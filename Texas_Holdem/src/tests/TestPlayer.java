package tests;

import static org.junit.Assert.*;

import java.net.Socket;

import org.junit.Test;

import main.ActionTaken;
import main.AuctionGUI;
import main.GameType;
import main.Player;

public class TestPlayer{
	
	@Test
	public void testPlayerConstructor(){
		Player player = new Player("Adam", 1000, 0);
		Player player1 = new Player(new Socket(), 123, 2, GameType.FIXLIMIT,false);
		player1.setLimit(10);
		assertEquals(GameType.FIXLIMIT,player1.getGameType());
		assertEquals(false,player1.isStateChanged());
		assertEquals(0,player1.getCurrentPot());
		assertEquals(10,player1.getLimit());
		assertEquals(123,player1.getPlayerTokens());
		assertEquals(player.getPlayerName(),"Adam");
		assertEquals(player.getPlayerTokens(),1000);
		assertEquals(player.getPlayerIndex(),0);
		assertEquals(player.isInGame(),true);
		assertEquals(player.isBigBlind(),false);
		assertEquals(player.isSmallBlind(),false);
		assertEquals(player.isDealerButton(),false);
	}
	
	@Test
	public void testCheck(){
		Player player = new Player("Adam",1000,0);
		player.check();
		assertEquals(player.playerState,ActionTaken.CHECKING);
	}
	
	@Test
	public void testBet(){
		Player player = new Player("Adam", 1000, 0);
		player.bet(400);
		assertEquals(player.getPlayerTokens(),600);
		assertEquals(player.getCurrentTotalBet(),400);
		assertEquals(player.playerState,ActionTaken.BETING);
	}
	
	@Test
	public void testRaise(){
		Player player = new Player("Adam", 1000, 0);
		Player player2 = new Player("Tomek", 1000, 1);
		player.bet(300);
		player2.raise(400);
		assertEquals(player2.getPlayerTokens(),600);
		assertEquals(player2.getCurrentTotalBet(),400);
		assertEquals(player2.playerState,ActionTaken.RISING);
	}
	
	@Test
	public void testFold(){
		Player player = new Player("Adam", 1000, 0);
		player.fold();
		assertEquals(player.playerState,ActionTaken.FOLDING);
	}
	
	@Test
	public void testAllIn(){
		Player player = new Player("Adam", 1000, 0);
		player.allIn();
		assertEquals(player.getPlayerTokens(),0);
		assertEquals(player.getCurrentTotalBet(),1000);
		assertEquals(player.playerState,ActionTaken.ALLIN);		
	}
	
	
	
	
}
