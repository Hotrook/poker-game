package main;

import java.util.List;
import java.net.Socket;
import java.util.Random;

public class Bot extends Player {
	
	public Random generator;
	private List <Card> tableCards;
	private int bet;
	private double  coef; //coeficient 
	
	public Bot( Socket socket ,int initialTokensy, int index, GameType gameType, boolean isBot) {
		super(socket, initialTokensy, index, gameType, isBot);
		generator = new Random();
		setPlayerName("BOT");
	}
	
	
	public void getMovement(int round){
		if( round == 0 ){
			if ( HandDeterminer.determineHand(getHand(), null, this) > 255 ||
				 sumHand() > 23 || generator.nextInt()%6 == 0){
				
				if( HandDeterminer.determineHand(getHand(), null, this) > 255){
					coef = (HandDeterminer.determineHand(getHand(), null, this) - 255) / 12;
				}
				else if( sumHand() > 23 ){
					coef = (sumHand()- 23) / 5;
				}
				else{
					coef = (generator.nextInt()%100)/100;
				}
				bet = (int) (coef*0.2*getPlayerTokens());
			
				if( bet < getCurrentBet() )
					bet = getCurrentBet();
				bet = controlBet(bet);
				
				
			}
			else
				setActionName("fold");
		}
		else if( round == 1){
			if( HandDeterminer.determineHand(getHand(),getTableCards(),this) > 255 ||
				generator.nextInt()%9 == 0 ){
				if( HandDeterminer.determineHand(getHand(), null, this) > 255){
					coef = (HandDeterminer.determineHand(getHand(), null, this) - 255) / 12;
				}
				else{
					coef = (generator.nextInt()%100)/100;
				}
				bet = (int) (coef*0.2*getPlayerTokens());
				
				bet = controlBet(bet);
			}
			else
				setActionName("fold");
			
		}
		else if( round == 2){
			if(HandDeterminer.determineHand(getHand(), getTableCards(), this) > 262 ||
			   generator.nextInt()%6 == 0){
				setActionName("call");
			}
			else
				setActionName("fold");
			
		}
		else if( round == 3){
			if(HandDeterminer.determineHand(getHand(),getTableCards(),this) > 400 ||
			   generator.nextInt()%3 == 0){
				setActionName("call"); 
			}
			else
				setActionName("fold");
			
		}
		
	}
	
	private int controlBet(int bet) {
		
		if( gameType == GameType.FIXLIMIT){
			if( bet >= limit ){
				bet = limit;
			}
		}
		if( gameType == GameType.POTLIMIT){
			if( bet >= getCurrentPot()){
				bet = getCurrentPot();
			}
		}
		
		if( bet <= getCurrentBet()){
			if( getPlayerTokens () > getCurrentBet() ){
				bet = getCurrentBet();
				setActionName("call");
				setCurrentBet(bet);
			}
			else{
				bet = getPlayerTokens();
				setActionName("allin");
			}
		}
		else {
			setCurrentBet(bet);
			setActionName("raise");
		}
		return bet;
	}


	@Override
	public void sendHandInfoToClient(){/*it should do nothing*/}
	
	public void setActive(String data,int round){
		//setActionName("call");
		getMovement(round);
	}
	
	@Override
	public void setBlocked(){}
	
	public void showCards(List <Card> cards){
		setTableCards(cards);
	}
	
	public void setTableCards(List <Card> tableCards){
		this.tableCards = tableCards;
	}
	
	public List <Card> getTableCards(){
		return this.tableCards;
	}
	
	public int sumHand(){
		int sum;
		sum = getHand().get(0).getRank() + getHand().get(1).getRank();
		return sum;
	}
	

}
