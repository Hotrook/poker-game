package main;

import java.util.List;

public class HandDeterminer {
	
	public int determineHand(List<Card> playerCards, List<Card> tableCards,Player player){
		int power;
		if( (power = HandDeterminer.StraightFlush(playerCards, tableCards, player)) > 0){
			return Hands.STRAIGHTFLUSH * 251 + power;
		}
		else if((power = HandDeterminer.FourOfAKind(playerCards, tableCards, player)) > 0){
			return Hands.FOUROFAKIND * 251 + power;
		}
		else if((power = HandDeterminer.FullHouse(playerCards, tableCards, player)) > 0){
			return Hands.FULLHOUSE * 251 + power;
		}
		else if((power = HandDeterminer.Flush(playerCards, tableCards, player)) > 0){
			return Hands.FLUSH * 251 + power; 
		}
		else if((power = HandDeterminer.Straight(playerCards, tableCards, player)) > 0){
			return Hands.STRAIGHT * 251 + power;
		}
		else if((power = HandDeterminer.ThreeOfAKind(playerCards, tableCards, player)) > 0){
			return Hands.THREEOFAKIND * 251 + power; 
		}
		else if((power = HandDeterminer.TwoPair(playerCards, tableCards, player)) > 0){
			return Hands.TWOPAIR * 251 + power; 
		}
		else if((power = HandDeterminer.OnePair(playerCards, tableCards, player)) > 0){
			return Hands.ONEPAIR * 251 + power;
		}
		else if((power = HandDeterminer.HighCard(playerCards, tableCards, player)) > 0){
			return Hands.HIGHCARD * 251 + power;
		}
		return -1;
	}
	
	private static int StraightFlush(List<Card> playerCards, 
										 List<Card> tableCards,
										 Player player){
		return 0;
		
	};
	
	private static int FourOfAKind(List<Card> playerCards, 
						   List<Card> tableCards,
						   Player player){
		return 0;
	}
	
	private static int FullHouse(List<Card> playerCards, 
			   			   List<Card> tableCards,
			   			   Player player){
		return 0;
	}
	
	private static int Flush(List<Card> playerCards, 
						   List<Card> tableCards,
						   Player player){
		return 0;
	}
	
	private static int Straight(List<Card> playerCards,
						   List<Card> tableCards, 
						   Player player){
		return 0;
	}
	
	private static int ThreeOfAKind(List<Card> playerCards, 
						   List<Card> tableCards,
						   Player player){
		return 0;
	}
	
	private static int TwoPair(List<Card> playerCards, 
						   List<Card> tableCards,
						   Player player){
		return 0;
	}
	
	private static int OnePair(List<Card> playerCards, 
						   List<Card> tableCards,
						   Player player ){
		return 0;

	}
	
	public static int HighCard(List<Card> playerCards, 
					      List<Card> tableCards,
					      Player player){
		return 0;
	}

}
