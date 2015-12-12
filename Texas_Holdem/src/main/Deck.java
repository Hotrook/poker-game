package main;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.InvalidNumberOfRankException;
import exceptions.InvalidNumberOfSuitException;

public class Deck {
	
	private volatile static  Deck deck;
	private static  List<Card> cards = new ArrayList<Card>();
	
	
	
	private Deck() throws InvalidNumberOfRankException, 
						  InvalidNumberOfSuitException {
		
		Deck.cards = new ArrayList<Card>();
		initializeCards();
		
	}
	
	
	
	public static  Deck getInstance() throws InvalidNumberOfRankException, 
							         InvalidNumberOfSuitException {
		
		if( Deck.deck == null ){
			synchronized(Deck.class){
				if(  Deck.deck == null )
					 Deck.deck = new Deck();
			}
		}
		return Deck.deck;
	}
	
	
	public void initializeCards() throws InvalidNumberOfRankException, InvalidNumberOfSuitException{
		cards.clear();
		for( int i = 0 ; i < 13 ; ++i )
			for( int j = 0 ; j < 4  ; ++j )
				Deck.cards.add( new Card( i, j ) );
		shuffle();
	}
	
	public void shuffle(){
		Collections.shuffle( Deck.cards );	
	}
	
	
	
	public List<Card> getCards(){
		return Deck.cards;
	}



	public List<Card> giveTwoCards() {
		return null;
	}



	public List<Card> giveCards(int i) {
		List <Card> result = new ArrayList<Card>();
		while( i != 0 ){ 
			result.add(cards.get(0));
			cards.remove(cards.get(0));
			i--;
		}
		return result;
	}
	
	
}
