package main;

import java.util.List;
import java.net.Socket;
import java.util.Random;

public class Bot extends Player {
	
	public Random generator;
	private List <Card> tableCards;
	
	public Bot(Socket accept, int initialTokensy, int counter, GameType gameType) {
		super(accept, initialTokensy, counter, gameType);
		setBot(true);
		generator = new Random();
	}
	
	
	public void getMovement(int round){
		if( round == 1 ){
			if ( HandDeterminer.determineHand(getHand(), null, this) > 255 ||
				 getHand().get(0).getRank() + getHand().get(1).getRank() > 20 ||
				 generator.nextInt()%17 == 0
				 ){
				//obstawianie
				
			}
		}
		else if( round == 2){
			if( HandDeterminer.determineHand(getHand(),getTableCards(),this) > 255 ||
				generator.nextInt()%9 == 0 ){
					//oobstawianie
				}
				
			
		}
		else if( round == 3){
			if(HandDeterminer.determineHand(getHand(), getTableCards(), this) > 262 ||
			   generator.nextInt()%7 == 0){
				
			}
			
		}
		else if( round == 4){
			if(HandDeterminer.determineHand(getHand(),getTableCards(),this) > 400 ||
			   generator.nextInt()%8 == 0){
				   
			   }
			
		}
		
	}
	
	@Override
	public void sendHandInfoToClient(){/*it should do nothing*/}
	
	public void setActive(String data,int round){
		getMovement(round);
	}
	
	@Override
	public void setBlocked(){}
	
	@Override 
	public void setGameType(GameType gameType ){
		
		if( gameType == GameType.FIXLIMIT){}
		
	}
	
	public void showCards(List <Card> cards){
		setTableCards(cards);
	}
	
	public void setTableCards(List <Card> tableCards){
		this.tableCards = tableCards;
	}
	
	public List <Card> getTableCards(){
		return this.tableCards;
	}
	
	

}
