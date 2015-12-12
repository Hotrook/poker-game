package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HandDeterminer {
	
	private static List<Card> operatingList = new ArrayList<Card>();
	

	public static double determineHand(List<Card> playerCards, List<Card> tableCards,Player player){
		double power;
		operatingList.clear();
		operatingList.addAll(playerCards);
		operatingList.addAll(tableCards);
		
		
		if( (power = HandDeterminer.StraightFlush(operatingList)) > 0){
			player.setPower( Hands.STRAIGHTFLUSH*251 + power);
			return Hands.STRAIGHTFLUSH * 251 + power;
		}
		else if((power = HandDeterminer.FourOfAKind(operatingList)) > 0){
			player.setPower( Hands.FOUROFAKIND*251 + power);
			return Hands.FOUROFAKIND * 251 + power;
		}
		else if((power = HandDeterminer.FullHouse(operatingList)) > 0){
			player.setPower( Hands.FULLHOUSE*251 + power);
			return Hands.FULLHOUSE * 251 + power;
		}
		else if((power = HandDeterminer.Flush(operatingList)) > 0){
			player.setPower( Hands.FLUSH*251 + power);
			return Hands.FLUSH * 251 + power; 
		}
		else if((power = HandDeterminer.Straight(operatingList )) > 0){
			player.setPower( Hands.STRAIGHT*251 + power);
			return Hands.STRAIGHT * 251 + power;
		}
		else if((power = HandDeterminer.ThreeOfAKind(operatingList)) > 0){
			player.setPower( Hands.THREEOFAKIND*251 + power);
			return Hands.THREEOFAKIND * 251 + power; 
		}
		else if((power = HandDeterminer.TwoPair(operatingList)) > 0){
			player.setPower( Hands.TWOPAIR*251 + power);
			return Hands.TWOPAIR * 251 + power; 
		}
		else if((power = HandDeterminer.OnePair(operatingList)) > 0){
			player.setPower( Hands.ONEPAIR*251 + power);
			return Hands.ONEPAIR * 251 + power;
		}
		else if((power = HandDeterminer.HighCard(operatingList)) > 0){
			player.setPower( Hands.HIGHCARD*251 + power);
			return Hands.HIGHCARD * 251 + power;
		}
		return -1;
	}
	
	
	private static double StraightFlush(List<Card> operatingList){
		Collections.sort(operatingList, new SortBySuitThenRank());
		int counter = 0;
		int max = 0; 
		
		
		for( int i = 1; i <= 3 ; ++i ){
			if( counter != 4 ){
				
				counter = 0; 
				max = operatingList.get(i - 1).getRank();
				
				while( operatingList.get(i+counter).getSuit() == operatingList.get( i + counter - 1 ).getSuit() &&
					   operatingList.get(i+counter).getRank() == operatingList.get( i + counter - 1 ).getRank() - 1){
					counter++;
				}
			}
		}
		
		if( counter == 4 ){
			return max;
		}
		
		return 0;
		
	};

	
	
	
	private static double FourOfAKind(List<Card> operatingList){
		
		Collections.sort( operatingList, new SortByRank());
		
		 
		int counter = 0;
		double max = 0 ; 
		
		for( int i = 1 ; i <= 4 ; ++i ){
			if( counter != 3 ){
				
				counter = 0 ;
				max = operatingList.get(i-1).getRank();
				
				while( i+counter < 7 &&
						operatingList.get(i+counter).getRank() == operatingList.get(i+counter - 1).getRank() ){
					counter++;
				}
				
				
			}
			
		}
		
		if(counter == 3){
			counter = 0 ; 
			while ( operatingList.get(counter).getRank() == max ){
				counter++;
			}
			max += operatingList.get(counter).getRank()*0.01;
			return max;
		}
		return 0;
		
	}
	
	
	
	
	private static double FullHouse(List<Card> operatingList){
		Collections.sort( operatingList, new SortByRank());
		int two = 0;
		int three = -1;
		int i = 0 ; 
		int counter = 0 ;
		double result;
		boolean three_found = false;
		boolean two_found = false;
		
		while( i <= 4 && three_found == false ){
			
			three = operatingList.get(i).getRank();
			
			counter = 1;
			while( i+counter <= 6 && operatingList.get( i + counter).getRank() == three){
				counter++;
			}
			
			if( counter == 3){
				three_found = true;
			}
			i++;
		}
		
		i=0;
		while( i <= 5  && two_found == false){
			if( operatingList.get(i).getRank() != three){
				two = operatingList.get(i).getRank();
				if( operatingList.get(i+1).getRank() == two){
					two_found = true;
				}
			}
			i++;
		}
		
		if( three_found && two_found ) {
			result = three + two*0.01;
			
			return result;
		}
		
		return 0;
	}
	
	
	
	
	private static double Flush(List<Card> operatingList){
		Collections.sort(operatingList, new SortBySuitThenRank());
		int counter = 0;
		int i = 0 ;
		int suit = 0;
		double result = 0;
		boolean flush_found = false;
		
		while (i <= 2 && flush_found == false ){
				
				counter = 1;
				suit = operatingList.get(i).getSuit();
				result = operatingList.get(i).getRank();
				while( counter < 5 && operatingList.get(i+counter).getSuit() == suit  ){
					result += operatingList.get(i+counter).getRank() * Math.pow( 10 , ((-2)*counter));
					counter++;
				}
				
				if( counter == 5 ){
					flush_found = true;
				}
				i++;
		}
		
		if( counter == 5 ){
			return result;
		}
		
		return 0;
	}
	
	
	
	
	private static double Straight(List<Card> operatingList){
		
		Collections.sort(operatingList, new SortByRank());
		int counter = 0;
		int i = 0;
		int j ;
		int antecessor;
		 
		double result = 0; 
		boolean straight_found = false;
		
		while( i <= 2 && straight_found == false ){
			
			j = i;
			counter = 1;
			result = operatingList.get(i).getRank();
			antecessor = operatingList.get(i).getRank();
			
			while ( j <= 6 && counter < 5){
				if ( operatingList.get(j).getRank() + 1 == antecessor ){
					antecessor = operatingList.get(j).getRank();
					counter++;
				}
				j++;
			}
			
			if( counter == 5 ){
				straight_found = true;
			}
			i++;
		}
		
		if( straight_found )
			return result;
		
		return 0;
	}
	
	
	
	
	private static double ThreeOfAKind(List<Card> operatingList){
		
		Collections.sort(operatingList, new SortByRank());
		int counter = 0 ;
		int i = 0;
		int rank;
		double result = 0;
		boolean three_found = false ;
		
		while ( i <= 4  && three_found == false ){
			
			counter = 1 ; 
			rank = operatingList.get(i).getRank();
			result = rank;
			
			while( operatingList.get(i+counter).getRank() == rank ){
				counter++;
			}
			
			if( counter == 3 ){
				three_found = true;
			}
			
			counter = 0;
			for( int j = 0 ; j < 7 ; ++j){
				
				if( counter <= 1 && operatingList.get(j).getRank() != rank ) {
					
					counter++;
					result += Math.pow( 10 , (-2)*counter )*operatingList.get(j).getRank();
				}
			}
			i++;
		}
		
		if( three_found ){
			return result;
		}
		return 0;
		
	}
	
	
	
	
	private static double TwoPair(List<Card> operatingList){
		Collections.sort(operatingList, new SortByRank());
		int i = 0;
		int rank;
		int pair1 = 0;
		int pair2 = 0;
		double result = 0;
		
		boolean first_found = false;
		boolean second_found = false;
		boolean third_found = false;
		
		while ( i <= 5 ){
			
			rank = operatingList.get(i).getRank();
			if( operatingList.get(i+1).getRank() == rank ){
				
				if( first_found == false ){
					
					pair1 = rank;
					first_found = true;
					result = pair1;
				}
				else if( second_found == false ){
					
					pair2 = rank;
					second_found = true;
					result += pair2*0.01;
				} 
			}
			i++;
		}
		
		if( first_found && second_found ){
			
			i = 0;
			while( i < 7 && third_found == false){
				
				rank = operatingList.get(i).getRank();
				if( rank != pair1 && rank != pair2 ){
					third_found = true;
					result += rank * 0.0001;
				}
			}
			
			return result;
		}
		
		
		return 0;
	}
	
	
	
	
	private static double OnePair(List<Card> operatingList){
		Collections.sort(operatingList, new SortByRank());
		
		int i = 0; 
		int counter = 0;
		int rank = 0;
		int pair = 0;
		double result = 0;
		boolean pair_found = false;
		
		while ( i <= 5 && pair_found == false ){
			
			pair = operatingList.get(i).getRank();
			if( operatingList.get(i+1).getRank() == pair ){
				
				pair_found = true;
				result = pair;
			}
			
			i++;
		}
		
		if( pair_found ){
			
			i = 0; 
			while ( i < 7 && counter < 3){
				
				rank = operatingList.get(i).getRank();
				if( rank != pair ){
					counter++;
					result += rank * Math.pow( 10, (-2)*counter );
				}
				i++;
			}
			
			return result;
		}
		
		return 0;

	}
	
	
	
	
	public static double HighCard(List<Card> operatingList){
		Collections.sort(operatingList, new SortByRank());
		double result;
		int i = 1;
		int rank;
		
		result = operatingList.get(0).getRank();
		
		while ( i <= 4 ){
			
			rank = operatingList.get(i).getRank();
			result += rank * Math.pow(10, (-2)*i );
			
			i++;
		}
		return result;
	}

	
	
	private static class SortBySuitThenRank implements Comparator<Card>{
		@Override
		public int compare(Card o1, Card o2) {
			Card card1 = o1;
			Card card2 = o2;
			if( card1.getSuit() > card2.getSuit()){
				return -1;
			}
			else if( card1.getSuit() < card2.getSuit() ){
				return 1;
			}
			else {
				if (card1.getRank() > card2.getRank() ){
					
					return -1;
				}
				else if( card1.getRank() < card2.getRank() ){
					
					return 1;
				}
				return 0;
			}
		
		}
		
	}
	
	private static class SortByRank implements Comparator<Card>{

		@Override
		public int compare(Card o1, Card o2) {
			if( o1.getRank() > o2.getRank()){
				return -1;
			}
			else if(o1.getRank() < o2.getRank()){
				return 1;
			}
			return 0;
		}
		
	}
}
