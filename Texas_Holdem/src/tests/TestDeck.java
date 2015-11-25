package tests;

import main.Card;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidNumberOfRankException;
import exceptions.InvalidNumberOfSuitException;
import main.Deck;

public class TestDeck {
	

	@Test
	public void test_Shuffle() throws InvalidNumberOfRankException, InvalidNumberOfSuitException {
		
		List<Card> cards1 = Arrays.asList(new Card[52]);
		List<Card> cards2 = Arrays.asList(new Card[52]);
		
		Collections.copy(cards1, Deck.getInstance().getCards());
 		
		Deck.getInstance().shuffle();
		
		Collections.copy(cards2, Deck.getInstance().getCards());
	
		assertNotEquals(cards1, cards2);
 		
	}

}
