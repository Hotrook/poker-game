package main;

public final class MoveRestrictions {
	
	private MoveRestrictions(){
		
	}
	
	public static void Restrict(Auction auction){
		AuctionGUI gui = auction.getCurrentPlayer().getTa();
		Player previousPlayer = auction.getPreviousPlayer();
		
		//check player's tokens in order to specify options available
		//in case of bet > player tokens, player can only do AllIn action
		if(auction.getCurrentPlayer().getPlayerTokens() < auction.getCurrentBet()){
			gui.bet.setEnabled(false);
			gui.check.setEnabled(false);
			gui.call.setEnabled(false);
			gui.raise.setEnabled(false);
			gui.fold.setEnabled(false);
		}
		
		//options available for first player in his first move: CHECK, BET, FOLD, ALL IN
		//option unavailable: RAISE, CALL
		if(auction.movesCounter == 0){
			gui.raise.setEnabled(false);
			gui.call.setEnabled(false);
		}
		
		//options available after CHECK of previous player: CHECK, BET, FOLD, ALL IN
		//option unavailable: RAISE, CALL
		if(previousPlayer.playerState == ActionTaken.CHECKING){
			gui.raise.setEnabled(false);
			gui.call.setEnabled(false);
		}
		
		//options available after BET of previous player: RAISE, CALL, FOLD, ALL IN
		//option unavailable: CHECK, BET
		if(previousPlayer.playerState == ActionTaken.BETING){
			gui.check.setEnabled(false);
			gui.bet.setEnabled(false);
		}
		
		//options available after CALL of previous player: RAISE, FOLD, ALL IN
		//option unavailable: CHECK, BET, CALL
		if(previousPlayer.playerState == ActionTaken.CALLING){
			gui.check.setEnabled(false);
			gui.bet.setEnabled(false);
			gui.call.setEnabled(false);
		}
		
		//options available after RAISE of previous player: CALL, FOLD, ALL IN, RAISE
		//option unavailable: CHECK, BET
		if(previousPlayer.playerState == ActionTaken.RISING){
			gui.check.setEnabled(false);
			gui.bet.setEnabled(false);
		}
		
		//options available after FOLD of previous player: 
		//option unavailable: CHECK, BET, CALL, FOLD, ALL IN, RAISE
		if(previousPlayer.playerState == ActionTaken.FOLDING){
			MoveRestrictions.RestrictAll(gui);
		}
		
		//options available after ALLIN of previous player: 
		//option unavailable: CHECK, BET, CALL, FOLD, ALL IN, RAISE
		if(previousPlayer.playerState == ActionTaken.ALLIN){
			MoveRestrictions.RestrictAll(gui);
		}
		
		
	}
	
	//Enable all options before restrictions
	public static void ResetRestrictions(AuctionGUI gui){
		gui.bet.setEnabled(true);
		gui.check.setEnabled(true);
		gui.call.setEnabled(true);
		gui.raise.setEnabled(true);
		gui.fold.setEnabled(true);
		gui.allin.setEnabled(true);
	}
	
	//Restrict all options
	public static void RestrictAll(AuctionGUI gui){
		gui.bet.setEnabled(false);
		gui.check.setEnabled(false);
		gui.call.setEnabled(false);
		gui.raise.setEnabled(false);
		gui.fold.setEnabled(false);
		gui.allin.setEnabled(false);
	}
}
