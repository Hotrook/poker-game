package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidNumberOfRankException;
import exceptions.InvalidNumberOfSuitException;
import main.Card;
import main.HandDeterminer;
import main.Player;

public class TestHandDeterminer {
	
	List<Card> DummyCards;
	List<Card> DummyPlayerCards;
	List<Card> tableCards;
	List<Card> playerCards ;
	Player player;
	
	public final double precision = 0.0000000000001;
	@Before
	public void init() throws InvalidNumberOfRankException, 
							  InvalidNumberOfSuitException{
		Card card1 = new Card(5,2);
		Card card2 = new Card(13,1);
		Card card3 = new Card(7,2);
		Card card4 = new Card(3,3);
		Card card5 = new Card(9,0);
		Card card6 = new Card(10,1);
		Card card7 = new Card(11,2);
		
		DummyCards = new ArrayList<Card>();
		DummyPlayerCards = new ArrayList<Card>();
		tableCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();
		
		DummyCards.add(card1);
		DummyCards.add(card2);
		DummyCards.add(card3);
		DummyCards.add(card4);
		DummyCards.add(card5);
		
		DummyPlayerCards.add(card6);
		DummyPlayerCards.add(card7);
		
		player = new Player("Siemanko",123,123);
		player.setTableCards(DummyCards);
	}
	
	
	
	@Test
	public void testStraightFlushShouldSucceed() throws InvalidNumberOfRankException, 
										   InvalidNumberOfSuitException {
		
		Card card1 = new Card(5,2);
		Card card2 = new Card(6,2);
		Card card3 = new Card(7,2);
		Card card4 = new Card(8,2);
		Card card5 = new Card(9,2);
		Card card6 = new Card(10,1);
		Card card7 = new Card(11,2);
		
		tableCards.add(card1);
		tableCards.add(card2);
		tableCards.add(card3);
		tableCards.add(card4);
		tableCards.add(card5);
		
		playerCards.add(card6);
		playerCards.add(card7);
		
		assertEquals(8*251+9, HandDeterminer.determineHand(tableCards,playerCards,player),precision);
	}
	
	@Test
	public void testFourOfAKind() throws InvalidNumberOfRankException, 
										 InvalidNumberOfSuitException{
		
		tableCards = generateList(new Card(3,3),
							      new Card(9,3), 
							      new Card(9,0), 
							      new Card(9,2),
							      new Card(4,1));
		
		playerCards = generateList(new Card( 6,2), new Card(9,1));
		assertEquals(7*251+9.06, HandDeterminer.determineHand(tableCards,playerCards,player),precision);
		assertEquals(7*251+9.06,player.getPower(),precision);
	}
	
	@Test
	public void testFullHouse() throws InvalidNumberOfRankException, 
									   InvalidNumberOfSuitException{
		List<Card> tableCards = new ArrayList<Card>();
		List<Card> playerCards = new ArrayList<Card>();
		
		tableCards = generateList(new Card(4,3),
								  new Card(4,2),
								  new Card(5,1),
								  new Card(4,0),
								  new Card(12,0));
		
		playerCards = generateList(new Card(7,3),new Card(7,2));
		assertEquals(6*251 + 4.07 , HandDeterminer.determineHand(tableCards,playerCards,player),precision);
		assertEquals(6*251 + 4.07,player.getPower(),precision);
	}

	@Test 
	public void testFlush() throws InvalidNumberOfRankException, 
								   InvalidNumberOfSuitException{
		List<Card> tableCards = new ArrayList<Card>();
		List<Card> playerCards = new ArrayList<Card>();
		
		tableCards = generateList(new Card(4,3),
								  new Card(2,1),
								  new Card(5,1),
								  new Card(6,1),
								  new Card(12,1));
		
		playerCards = generateList(new Card(7,1),new Card(7,2));
		assertEquals(5*251 + 12.07060502 , HandDeterminer.determineHand(tableCards,playerCards,player),precision);
		assertEquals(5*251 + 12.07060502,player.getPower(),precision);
	}
	
	
	
	
	@Test
	public void testStraight() throws InvalidNumberOfRankException, 
									  InvalidNumberOfSuitException{
		
		tableCards = generateList(new Card(11,1),
								  new Card(11,0),
								  new Card(10,2),
								  new Card(9,1),
								  new Card(8,3));
		playerCards = generateList(new Card(7,1), new Card(6,2));
		
		assertEquals(4*251 + 11, HandDeterminer.determineHand(tableCards, playerCards, player),precision);
		assertEquals(4*251 + 11,player.getPower(),precision);
	}
	
	
	
	
	@Test 
	public void testThreeOfAKind() throws InvalidNumberOfRankException, 
									      InvalidNumberOfSuitException{
		
		tableCards = generateList(new Card(3,1),
								  new Card(4,2),
								  new Card(2,3),
								  new Card(11,1),
								  new Card(11,2));
		
		playerCards = generateList(new Card(11,0),new Card(12,2));
		
		assertEquals(3*251 + 11.1204, HandDeterminer.determineHand(tableCards,playerCards,player),precision);
		assertEquals(3*251 + 11.1204,player.getPower(),precision);
	}
	
	@Test
	public void testTwoPair() throws InvalidNumberOfRankException,
									 InvalidNumberOfSuitException{
		
		tableCards = generateList(new Card(3,1),
								  new Card(4,0),
								  new Card(3,3),
								  new Card(4,2),
								  new Card(5,1));
		
		playerCards = generateList(new Card(7,2),new Card(11,2));
		
		assertEquals(2*251 + 4.0311, HandDeterminer.determineHand(tableCards,playerCards,player),precision);
		assertEquals(2*251 + 4.0311,player.getPower(),precision);
	}
	
	
	
	
	@Test
	public void testOnePair() throws InvalidNumberOfRankException, 
								     InvalidNumberOfSuitException{
		
		tableCards = generateList(new Card(3,1),
								  new Card(4,0),
								  new Card(2,3),
								  new Card(7,2),
								  new Card(5,1));

		playerCards = generateList(new Card(11,1),new Card(11,2));

		assertEquals(1*251 + 11.070504, HandDeterminer.determineHand(tableCards,playerCards,player),precision);
		assertEquals(1*251 + 11.070504,player.getPower(),precision);
	}
	
	
	
	@Test
	public void testHighCard() throws InvalidNumberOfRankException, 
									  InvalidNumberOfSuitException {
		tableCards = generateList(new Card(3,1),
				  				  new Card(12,0),
								  new Card(2,3),
								  new Card(4,2),
								  new Card(5,1));

		playerCards = generateList(new Card(7,2),new Card(11,2));

		assertEquals(12.11070504, HandDeterminer.determineHand(tableCards,playerCards,player),precision);
		assertEquals(12.11070504, player.getPower(),precision);
	}
	

	public List<Card> generateList(Card card1,
								   Card card2,
							       Card card3,
						    	   Card card4,
						      	   Card card5){
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(card1);
		tableCards.add(card2);
		tableCards.add(card3);
		tableCards.add(card4);
		tableCards.add(card5);
		
		return tableCards;
	};
	
	
	private List<Card> generateList(Card card1, Card card2) {
		List<Card> playerCards = new ArrayList<Card>();
		playerCards.add(card1);
		playerCards.add(card2);
		
		return playerCards;
	}
}
