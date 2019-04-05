package com.hotrook;


import com.hotrook.exceptions.InvalidNumberOfRankException;
import com.hotrook.exceptions.InvalidNumberOfSuitException;

public class Card {

// ranks
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int ACE = 14;
// suits'
	public static final int CLUB = 0;
	public static final int DIAMOND = 1;
	public static final int HEART = 2;
	public static final int SPADE = 3;
	
	
	private final int suit;
	private final int rank;
	private final int power;
	
	public Card( int rank , int suit ) throws InvalidNumberOfRankException,
            InvalidNumberOfSuitException
	{
		
		if( rank < 0 || rank > 13 ){
			throw new InvalidNumberOfRankException();
		}
		
		if( suit < 0 || suit > 3 ){
			throw new InvalidNumberOfSuitException();
		}
		
		this.suit = suit ;
		this.rank = rank ;
		this.power = 13 * rank + suit;
	}

	
	
	public int getSuit() {
		return suit;
	}

	public int getPower(){
		return power;
	}
	
	public int getRank() {
		return rank;
	}
	
	
	
	public int compare( Card card ){
		
		int thisCard = 13 * this.rank + this.suit;
		int secondCard = 13 * card.rank + card.suit;
		
		if( thisCard > secondCard )       { return 1;  }
		else if( thisCard == secondCard ) { return 0;  }
		else 							  { return -1; }
		
	}
	
	
	
	
	
}
