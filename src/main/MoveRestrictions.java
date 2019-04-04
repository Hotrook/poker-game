package main;

public final class MoveRestrictions {
	
	private MoveRestrictions(){
		
	}
	
	//public static void Restrict(Auction auction){
	//PP - Previous Player
	public static void restrict(AuctionGUI gui, int playerTokens, int currentBet , ActionTaken ppaction, int movesCounter){
		//Player previousPlayer = auction.getPreviousPlayer();
		
		//check player's tokens in order to specify options available
		//in case of bet > player tokens, player can only do AllIn action
		//if(auction.getCurrentPlayer().getPlayerTokens() < auction.getCurrentBet()){
		if(playerTokens < currentBet){
			gui.bet.setEnabled(false);
			gui.check.setEnabled(false);
			gui.call.setEnabled(false);
			gui.raise.setEnabled(false);
		//	gui.fold.setEnabled(false);
		}
		
		//options available for first player in his first move: CHECK, BET, FOLD, ALL IN
		//option unavailable: RAISE, CALL
		//if(auction.movesCounter == 0){
		if(movesCounter == 0 && ppaction == ActionTaken.NONE){	
			gui.raise.setEnabled(false);
			gui.call.setEnabled(false);
		}
		
		//options available after CHECK of previous player: CHECK, BET, FOLD, ALL IN
		//option unavailable: RAISE, CALL
		//if(previousPlayer.playerState == ActionTaken.CHECKING){
		if(ppaction == ActionTaken.CHECKING){
			gui.raise.setEnabled(false);
			gui.call.setEnabled(false);
		}
		
		if(ppaction == ActionTaken.BB){
			MoveRestrictions.resetRestrictions(gui);
			gui.bet.setEnabled(false);
			gui.call.setEnabled(false);
		}
		
		//options available after BET of previous player: RAISE, CALL, FOLD, ALL IN
		//option unavailable: CHECK, BET
		//if(previousPlayer.playerState == ActionTaken.BETING){
		if(ppaction == ActionTaken.BETING){
			gui.check.setEnabled(false);
			gui.bet.setEnabled(false);
		}
		
		//options available after CALL of previous player: RAISE, FOLD, ALL IN, CALL
		//option unavailable: CHECK, BET
		if(ppaction == ActionTaken.CALLING){
			gui.check.setEnabled(false);
			gui.bet.setEnabled(false);
		}
		
		//options available after RAISE of previous player: CALL, FOLD, ALL IN, RAISE
		//option unavailable: CHECK, BET
		if(ppaction == ActionTaken.RISING){
			gui.check.setEnabled(false);
			gui.bet.setEnabled(false);
		}
		
		//options available after FOLD of previous player: 
		//option unavailable: CHECK, BET, CALL, FOLD, ALL IN, RAISE
		if(ppaction == ActionTaken.FOLDING){
			if(currentBet!=0){
				gui.check.setEnabled(false);
				gui.bet.setEnabled(false);
			}
			else{
				gui.call.setEnabled(false);
				gui.raise.setEnabled(false);
			}
		}
		
		//options available after ALLIN of previous player: 
		//option unavailable: CHECK, BET, CALL, FOLD, ALL IN, RAISE
		if(ppaction == ActionTaken.ALLIN){
			gui.check.setEnabled(false);
			gui.bet.setEnabled(false);
		}
		
		
	}
	
	//Enable all options before restrictions
	public static void resetRestrictions(AuctionGUI gui){
		gui.bet.setEnabled(true);
		gui.check.setEnabled(true);
		gui.call.setEnabled(true);
		gui.raise.setEnabled(true);
		gui.fold.setEnabled(true);
		gui.allin.setEnabled(true);
	}
	
	//Restrict all options
	public static void restrictAll(AuctionGUI gui){
		gui.bet.setEnabled(false);
		gui.check.setEnabled(false);
		gui.call.setEnabled(false);
		gui.raise.setEnabled(false);
		gui.fold.setEnabled(false);
		gui.allin.setEnabled(false);
	}
}
