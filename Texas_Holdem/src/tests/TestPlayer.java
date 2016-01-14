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
	public void test_PlayerConstructor(){
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
	public void test_Check(){
		Player player = new Player("Adam",1000,0);
		player.Check();
		assertEquals(player.playerState,ActionTaken.CHECKING);
	}
	
	@Test
	public void test_Bet(){
		Player player = new Player("Adam", 1000, 0);
		player.Bet(400);
		assertEquals(player.getPlayerTokens(),600);
		assertEquals(player.getCurrentTotalBet(),400);
		assertEquals(player.playerState,ActionTaken.BETING);
	}
	
	@Test
	public void test_Raise(){
		Player player = new Player("Adam", 1000, 0);
		Player player2 = new Player("Tomek", 1000, 1);
		player.Bet(300);
		player2.Raise(400);
		assertEquals(player2.getPlayerTokens(),600);
		assertEquals(player2.getCurrentTotalBet(),400);
		assertEquals(player2.playerState,ActionTaken.RISING);
	}
	
	@Test
	public void test_Fold(){
		Player player = new Player("Adam", 1000, 0);
		player.Fold();
		assertEquals(player.playerState,ActionTaken.FOLDING);
	}
	
	@Test
	public void test_AllIn(){
		Player player = new Player("Adam", 1000, 0);
		player.AllIn();
		assertEquals(player.getPlayerTokens(),0);
		assertEquals(player.getCurrentTotalBet(),1000);
		assertEquals(player.playerState,ActionTaken.ALLIN);		
	}
	
	
	
	
}
