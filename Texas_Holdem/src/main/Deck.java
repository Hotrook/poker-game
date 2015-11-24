package main;

import java.util.ArrayList;
import java.util.Collections;

import exceptions.InvalidNumberOfRankException;
import exceptions.InvalidNumberOfSuitException;

public class Deck {
	
	private volatile Deck deck;
	private static ArrayList<Card> cards;
	
	
	
	private Deck() throws InvalidNumberOfRankException, 
						  InvalidNumberOfSuitException {
		
		Deck.cards = new ArrayList<Card>();
		
		for( int i = 0 ; i < 13 ; ++i )
		for( int j = 0 ; j < 4  ; ++j )
			cards.add( new Card( i, j ) );
		
	}
	
	
	
	public Deck getInstance() throws InvalidNumberOfRankException, 
							         InvalidNumberOfSuitException {
		
		if( deck == null ){
			synchronized(this){
				if( deck == null )
					this.deck = new Deck();
			}
		}
		return deck;
	}
	
	
	
	public void shuffle(){
		Collections.shuffle( cards );	
	}
	
	
	
	public ArrayList<Card> getCards(){
		return cards;
	}
	
	
}
