package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.InvalidNumberOfRankException;
import exceptions.InvalidNumberOfSuitException;
import main.Card;

public class TestCard {

	
	
	@Test(expected = InvalidNumberOfRankException.class)
	public void test_InvalidNumberOfRankException() 
			throws InvalidNumberOfRankException, 
				   InvalidNumberOfSuitException {
		Card card = new Card( -1, 3 );
	}
	
	
	
	@Test(expected = InvalidNumberOfSuitException.class)
	public void test_InvalidNumberOfSuitException() 
			throws InvalidNumberOfRankException, 
			InvalidNumberOfSuitException {
		Card card = new Card( 6, 13 );
	}
	
	
	
	@Test 
	public void test_Compare() throws InvalidNumberOfRankException, 
									  InvalidNumberOfSuitException {
		
		Card card1 = new Card( 8, 3 );
		Card card2 = new Card( 8, 2 );
		Card card3 = new Card( 9, 0 );
		Card card4 = new Card( 9, 0 );
		
		assertEquals( 1 , card1.Compare( card2 ) );
		assertEquals( -1, card2.Compare( card3 ) );
		assertEquals( 0 , card3.Compare( card4 ) );
	}
	
	
	
}
