package com.hotrook;

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
		if( tableCards != null )
			operatingList.addAll(tableCards);
		
		
		if( (power = HandDeterminer.straightFlush(operatingList)) > 0){
			player.setPower( Hands.STRAIGHTFLUSH*251 + power);;
			return Hands.STRAIGHTFLUSH * 251 + power;
		}
		else if((power = HandDeterminer.fourOfAKind(operatingList)) > 0){
			player.setPower( Hands.FOUROFAKIND*251 + power);
			return Hands.FOUROFAKIND * 251 + power;
		}
		else if((power = HandDeterminer.fullHouse(operatingList)) > 0){
			player.setPower( Hands.FULLHOUSE*251 + power);
			return Hands.FULLHOUSE * 251 + power;
		}
		else if((power = HandDeterminer.flush(operatingList)) > 0){
			player.setPower( Hands.FLUSH*251 + power);
			return Hands.FLUSH * 251 + power; 
		}
		else if((power = HandDeterminer.straight(operatingList )) > 0){
			player.setPower( Hands.STRAIGHT*251 + power);
			return Hands.STRAIGHT * 251 + power;
		}
		else if((power = HandDeterminer.threeOfAKind(operatingList)) > 0){
			player.setPower( Hands.THREEOFAKIND*251 + power);
			return Hands.THREEOFAKIND * 251 + power; 
		}
		else if((power = HandDeterminer.twoPair(operatingList)) > 0){
			player.setPower( Hands.TWOPAIR*251 + power);
			return Hands.TWOPAIR * 251 + power; 
		}
		else if((power = HandDeterminer.onePair(operatingList)) > 0){
			player.setPower( Hands.ONEPAIR*251 + power);
			return Hands.ONEPAIR * 251 + power;
		}
		else if((power = HandDeterminer.highCard(operatingList)) > 0){
			player.setPower( Hands.HIGHCARD*251 + power);
			return Hands.HIGHCARD * 251 + power;
		}
		return -1;
	}
	
	
	private static double straightFlush(List<Card> operatingList){
		
		if( operatingList.size() < 5)
			return 0;
		
		Collections.sort(operatingList, new SortBySuitThenRank());
		int counter = 0;
		int max = 0; 
		
		
		for( int i = 1; i <= operatingList.size() - 4 ; ++i ){
			if( counter != 4 ){
				
				counter = 0; 
				max = operatingList.get(i - 1).getRank();
				
				while( operatingList.get(i+counter).getSuit() == operatingList.get( i + counter - 1 ).getSuit() &&
					   operatingList.get(i+counter).getRank() == operatingList.get( i + counter - 1 ).getRank() - 1 && i+counter<6){
					counter++;
					if( i + counter >= operatingList.size()-1 )
					  break;
				}
				
				
			}
		}
		
		if( counter == 4 ){
			return max;
		}
		
		return 0;
		
	};

	
	
	
	private static double fourOfAKind(List<Card> operatingList){
		
		Collections.sort( operatingList, new SortByRank());
		
		if( operatingList.size() < 4) {
			return 0;
		}
		int counter = 0;
		double max = 0 ; 
		
		for( int i = 1 ; i <= operatingList.size() - 3 ; ++i ){
			if( counter != 3 ){
				
				counter = 0 ;
				max = operatingList.get(i-1).getRank();
				
				while( i+counter < operatingList.size() &&
						operatingList.get(i+counter).getRank() == max ){
					counter++;
				}
				
				
			}
			
		}
		
		if(counter == 3){
			counter = 0 ; 
			while ( operatingList.get(counter).getRank() == max  && counter < operatingList.size()-1){
				counter++;
			}
			if( counter >= operatingList.size() )
			  counter = operatingList.size()-1;
			max += operatingList.get(counter).getRank()*0.01;
			return max;
		}
		return 0;
		
	}
	
	
	
	
	private static double fullHouse(List<Card> operatingList){
		if( operatingList.size() < 5){
			return 0;
		}
		Collections.sort( operatingList, new SortByRank());
		int two = 0;
		int three = -1;
		int i = 0 ; 
		int counter = 0 ;
		double result;
		boolean threefound = false;
		boolean twofound = false;
		
		while( i <= 4 && threefound == false ){
			
			three = operatingList.get(i).getRank();
			
			counter = 1;
			while( i+counter <= operatingList.size()-1 && operatingList.get( i + counter).getRank() == three){
				counter++;
			}
			
			if( counter == 3){
				threefound = true;
			}
			i++;
		}
		
		i=0;
		while( i <= operatingList.size()-2  && twofound == false){
			if( operatingList.get(i).getRank() != three){
				two = operatingList.get(i).getRank();
				if( operatingList.get(i+1).getRank() == two){
					twofound = true;
				}
			}
			i++;
		}
		
		if( threefound && twofound ) {
			result = three + two*0.01;
			
			return result;
		}
		
		return 0;
	}
	
	
	
	
	private static double flush(List<Card> operatingList){
		
		if( operatingList.size() < 5 ){
			return 0;
		}
		
		Collections.sort(operatingList, new SortBySuitThenRank());
		int counter = 0;
		int i = 0 ;
		int suit = 0;
		double result = 0;
		boolean flushfound = false;
		
		while (i <= operatingList.size()-5 && flushfound == false ){
				
				counter = 1;
				suit = operatingList.get(i).getSuit();
				result = operatingList.get(i).getRank();
				while( counter < 5 && operatingList.get(i+counter).getSuit() == suit  ){
					result += operatingList.get(i+counter).getRank() * Math.pow( 10 , ((-2)*counter));
					counter++;
				}
				
				if( counter == 5 ){
					flushfound = true;
				}
				i++;
		}
		
		if( counter == 5 ){
			return result;
		}
		
		return 0;
	}
	
	
	
	
	private static double straight(List<Card> operatingList){
		
		Collections.sort(operatingList, new SortByRank());
		int counter = 0;
		int i = 0;
		int j ;
		int antecessor;
		 
		double result = 0; 
		boolean straightfound = false;
		
		while( i <= operatingList.size()-5 && straightfound == false ){
			
			j = i;
			counter = 1;
			result = operatingList.get(i).getRank();
			antecessor = operatingList.get(i).getRank();
			
			while ( j <= operatingList.size()-1 && counter < 5){
				if ( operatingList.get(j).getRank() + 1 == antecessor ){
					antecessor = operatingList.get(j).getRank();
					counter++;
				}
				j++;
			}
			
			if( counter == 5 ){
				straightfound = true;
			}
			i++;
		}
		
		if( straightfound )
			return result;
		
		return 0;
	}
	
	
	
	
	private static double threeOfAKind(List<Card> operatingList){
		
		if(operatingList.size() < 3){
			return 0;
		}
		
		Collections.sort(operatingList, new SortByRank());
		int counter = 0 ;
		int i = 0;
		int rank;
		double result = 0;
		boolean threefound = false ;
		
		while ( i <= operatingList.size()-3 && threefound == false ){
			
			counter = 0 ; 
			rank = operatingList.get(i).getRank();
			result = rank;
			
			while( i+counter < operatingList.size() && operatingList.get(i+counter).getRank() == rank){
				counter++;
			}
			
			if( counter == 3 ){
				threefound = true;
			}
			
			counter = 0;
			for( int j = 0 ; j < operatingList.size() ; ++j){
				
				if( counter <= 1 && operatingList.get(j).getRank() != rank ) {
					
					counter++;
					result += Math.pow( 10 , (-2)*counter )*operatingList.get(j).getRank();
				}
			}
			i++;
		}
		
		if( threefound ){
			return result;
		}
		return 0;
		
	}
	
	
	
	
	private static double twoPair(List<Card> operatingList){
		if( operatingList.size() < 4){
			return 0;
		}
		Collections.sort(operatingList, new SortByRank());
		int i = 0;
		int rank;
		int pair1 = 0;
		int pair2 = 0;
		double result = 0;
		
		boolean firstfound = false;
		boolean secondfound = false;
		boolean thirdfound = false;
		
		while ( i <= operatingList.size()-2 ){
			
			rank = operatingList.get(i).getRank();
			if( operatingList.get(i+1).getRank() == rank ){
				
				if( firstfound == false ){
					
					pair1 = rank;
					firstfound = true;
					result = pair1;
				}
				else if( secondfound == false ){
					
					pair2 = rank;
					secondfound = true;
					result += pair2*0.01;
				} 
			}
			i++;
		}
		
		if( firstfound && secondfound ){
			
			i = 0;
			while( i < operatingList.size() && thirdfound == false){
				
				rank = operatingList.get(i).getRank();
				if( rank != pair1 && rank != pair2 ){
					thirdfound = true;
					result += rank * 0.0001;
				}
				i++;
			}
			
			return result;
		}
		
		
		return 0;
	}
	
	
	
	
	private static double onePair(List<Card> operatingList){
		Collections.sort(operatingList, new SortByRank());
		
		int i = 0; 
		int counter = 0;
		int rank = 0;
		int pair = 0;
		double result = 0;
		boolean pairfound = false;
		
		while ( i <= operatingList.size()-2 && pairfound == false ){
			
			pair = operatingList.get(i).getRank();
			if( operatingList.get(i+1).getRank() == pair ){
				
				pairfound = true;
				result = pair;
			}
			
			i++;
		}
		
		if( pairfound ){
			
			i = 0; 
			while ( i < operatingList.size() && counter < 3){
				
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
	
	
	
	
	public static double highCard(List<Card> operatingList){
		Collections.sort(operatingList, new SortByRank());
		double result;
		int i = 1;
		int rank;
		
		result = operatingList.get(0).getRank();
		
		while ( i <= operatingList.size()-3 ){
			
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
