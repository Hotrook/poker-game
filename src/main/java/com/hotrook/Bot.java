package com.hotrook;

import java.util.List;
import java.net.Socket;
import java.util.Random;

public class Bot extends Player {
	
	public Random generator;
	private List <Card> tableCards;
	private int bet;
	private boolean playin;
	private double  coef; //coeficient 
	
	public Bot( Socket socket ,int initialTokensy, int index, GameType gameType, boolean isBot) {
		super(socket, initialTokensy, index, gameType, isBot);
		generator = new Random();
		setPlayerName("BOT");
	}
	
	
	public void getMovement(int round){
		if( round == 0 ){
			if ( HandDeterminer.determineHand(getHand(), null, this) > 255 ||
				 sumHand() > 19 || generator.nextInt()%3 == 0 || isSmallBlind() || isBigBlind()){
				
				if( HandDeterminer.determineHand(getHand(), null, this) > 255){
					coef = (HandDeterminer.determineHand(getHand(), null, this) - 255) / 15;
				}
				else if( sumHand() > 18 ){
					coef = (sumHand()- 18) / 7;
				}
				else{
				  coef = ((generator.nextInt()+1)%100)/100;
				}
				bet = (int) (coef*0.2*getPlayerTokens())+1;
			
		
				bet = controlBet(bet);
				
				setPlayin(true);
			}
			else
				setActionName("fold");
		}
		else if( round == 1 && isPlayin()){
			if( HandDeterminer.determineHand(getHand(),getTableCards(),this) > 255 ||
				generator.nextInt()%9 == 0 ||isSmallBlind() || isBigBlind()){
				if( HandDeterminer.determineHand(getHand(), null, this) > 255){
					double base = 0;
					coef = (HandDeterminer.determineHand(getHand(), null, this));
					base = ((( coef / 255)+1 )* 255);
					coef = coef/base;
				}
				else{
					coef = ((generator.nextInt()+1)%100)/100;
				}
				bet = (int) (coef*0.2*getPlayerTokens()) +1;
				
				bet = controlBet(bet);
			}
			else{
				setActionName("fold");
				setPlayin(false);
			}
			
		}
		else if( round == 2 && isPlayin()){
			if(HandDeterminer.determineHand(getHand(), getTableCards(), this) > 262 ||
			   generator.nextInt()%6 == 0 || isSmallBlind() || isBigBlind()){
			   if( HandDeterminer.determineHand(getHand(), getTableCards(), this) > 262){
				   double base = 0;
					coef = (HandDeterminer.determineHand(getHand(), null, this));
					base = ((( coef / 255)+1 )* 255);
					coef = coef/base;
			   }
			   else{
			     coef = ((generator.nextInt()+1)%100)/100;
			   }
			   bet = (int) (coef*0.2*getPlayerTokens())+1;
			   
			   bet = controlBet(bet);
			}
			else{
				setActionName("fold");
			  setPlayin(false);
			}
			
		}
		else if( round == 3 && isPlayin()){
			if(HandDeterminer.determineHand(getHand(),getTableCards(),this) > 400 ||
			   generator.nextInt()%3 == 0 || isSmallBlind() || isBigBlind()){
				if( HandDeterminer.determineHand(getHand(), getTableCards(), this) > 400){
						double base;
					    coef = (HandDeterminer.determineHand(getHand(), null, this));
						base =  ((( coef / 255)+1 )* 255);
						coef = coef/base;
				   }
				   else{
				     coef = ((generator.nextInt()+1)%100)/100;
				   }
				   bet = (int) (coef*0.2*getPlayerTokens())+1;
				   
				   bet = controlBet(bet);
			}
			else{
				setActionName("fold");
				setPlayin(false);
			}
			
		}
		
	}
	
	private int controlBet(int bet) {
		if( bet < 0 ){
		  bet = 1;
		}
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
		
		System.out.println("In bot: " + getCurrentAuctionBet() + " " + bet);
		if( bet <= getCurrentAuctionBet() && getCurrentAuctionBet() > 0){
			if( getPlayerTokens () > getCurrentAuctionBet() ){
				bet = getCurrentAuctionBet();
				setActionName("call");
				//setCurrentBet(bet);
			}
			else{
				bet = getPlayerTokens();
				setActionName("allin");
			}
		}
		else {
			if( bet > getPlayerTokens())
				bet = getPlayerTokens();
				
			setCurrentBet(bet);
			setActionName("raise");
		}
		
		if ( getCurrentBet() == 0 ){
		  setActionName("fold");
		}
		return bet;
	}


	@Override
	public void sendHandInfoToClient(){/*it should do nothing*/}
	
	public void setActive(String data,int round){
		System.out.println("\t" + "Bot " + getPlayerIndex() + "zaczyna: ");
		System.out.println("\t"+getCurrentBet());
		System.out.println("\tBot ma " + getPlayerTokens());
		System.out.println();
		getMovement(round);
	}
	
	@Override
	public void setBlocked(){
	  //setPlayin(false);
		System.out.print("\t" + "Bot: ");
		System.out.print("obstawia "	+ getCurrentBet());
		System.out.print("robiac " + getActionName());
		System.out.print(" bet aukcji " + getCurrentAuctionBet());
		System.out.println("majac " + getPlayerTokens());
		
		setCurrentPlayerBet(getCurrentBet());
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
	
	public int sumHand(){
		int sum;
		sum = getHand().get(0).getRank() + getHand().get(1).getRank();
		return sum;
	}


  public boolean isPlayin() {
    return playin;
  }


  public void setPlayin(boolean playin) {
    this.playin = playin;
  }
	

}
