package main;

import java.util.List;
import java.net.Socket;
import java.util.Random;

public class Bot extends Player {
	
	public Random generator;
	private List <Card> tableCards;
	
	
	public Bot( Socket socket ,int initialTokensy, int index, GameType gameType, boolean isBot) {
		super(socket, initialTokensy, index, gameType, isBot);
		generator = new Random();
	}
	
	
	public void getMovement(int round){
		System.out.println("alsal");
		if( round == 0 ){
			if ( HandDeterminer.determineHand(getHand(), null, this) > 255 ||
				 getHand().get(0).getRank() + getHand().get(1).getRank() > 20 ||
				 generator.nextInt()%6 == 0){
				// Finished ther. Bot needs to know what is currentBet and GameType end if Fix limit - also the limit.
				// then he can evaluete his 
				setActionName("bet");
				System.out.println("Tu jestem");
			}
			else
				setActionName("fold");
		}
		else if( round == 1){
			if( HandDeterminer.determineHand(getHand(),getTableCards(),this) > 255 ||
				generator.nextInt()%9 == 0 ){
					//oobstawianie
			}
			else
				setActionName("fold");
			
		}
		else if( round == 2){
			if(HandDeterminer.determineHand(getHand(), getTableCards(), this) > 262 ||
			   generator.nextInt()%7 == 0){
				setActionName("call");
			}
			else
				setActionName("fold");
			
		}
		else if( round == 3){
			if(HandDeterminer.determineHand(getHand(),getTableCards(),this) > 400 ||
			   generator.nextInt()%8 == 0){
				setActionName("call"); 
			}
			else
				setActionName("fold");
			
		}
		
	}
	
	@Override
	public void sendHandInfoToClient(){/*it should do nothing*/}
	
	@Override 
	public void sendDataToEachClient(){}
	
	public void setActive(String data,int round){
		//setActionName("call");
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
