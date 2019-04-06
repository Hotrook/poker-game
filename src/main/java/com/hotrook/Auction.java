package com.hotrook;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class Auction {
    public int movesCounter = 0;
    private List<Player> playerQueue;
    private List<Player> playersInRound;
    private int roundNumber;
    private int currentPot;
    private int currentBet;
    private int raiseValue;
    private int difference;
    //FIEALDS USED IN START AUCTION METHOD
    private boolean endOfAuction = false;
    private Player currentPlayer = null;
    private Player previousPlayer = null;
    private ActionTaken ppaction = null;

    public Auction(List<Player> playersInRound) {
        setCurrentPot(0);
        setRoundNumber(0);
        playerQueue = new ArrayList<>();
        setPlayersInRound(playersInRound);
        for (Player player : playersInRound) {
            player.setCurrentPot(0);
            player.setCurrentBet(0);
            player.setCurrentPlayerBet(0);
            player.setCurrentTotalBet(0);
        }
    }

////METHODS USED IN AUCTION////

    public void setInitialPlayerQueue(List<Player> players) {
        boolean playerAfterBigBlind = false;
        int firstAddedPlayer = 0;
        int index = 0;
        for (Player player : players) {
            if (player.isBigBlind()) {
                firstAddedPlayer = index;
                playerAfterBigBlind = true;
            } else if (playerAfterBigBlind) {
                playerQueue.add(player);
            }
            index++;
        }
        for (int i = 0; i <= firstAddedPlayer; i++) {
            playerQueue.add(players.get(i));
        }
    }

    public boolean checkIfBetsAreEqual(List<Player> players) {
        boolean temp = true;
        int checkingBet = findInGameBet(players);
        System.out.println(" CHECK:  " + checkingBet + " ");
        for (Player player : players) {
            System.out.print("  " + player.getCurrentBet());
            if (player.getCurrentBet() != checkingBet && player.isInRound()) {
                temp = false;
                System.out.println("CHECK NIE ROWNY " + player.getPlayerName() + " " + player.getCurrentBet());
                break;
            }

        }
        return temp;
    }

    private int findInGameBet(List<Player> players) {
        int bet = 0;
        for (Player player : players) {
            if (player.isInRound()) {
                bet = player.getCurrentBet();
            }
        }
        return bet;
    }

    //method used to send data from each player to his client
    private void sendDataToEachClient(List<Player> players) {
        for (Player player : players) { // TODO: modify to use bot
            if (!player.isBot())
                player.sendDataToEachClient(createDataPackage(players));
            else {
                //System.out.println("Działam" + player.getPlayerName() + " " + currentPlayer.getCurrentBet());
                player.setCurrentAuctionBet(getCurrentBet());
                player.setCurrentPot(getCurrentPot());
            }

        }
    }

    private void sendHandInfoToEachClient(List<Player> players) {
        for (Player player : players) {
            player.sendHandInfoToClient();
        }
    }

    public void placeBlindsOnTable() {
        int bbvalue = Server.input.getBigBlindValue();
        int sbvalue = Server.input.getSmallBlindValue();


        //placing blinds on table
        for (Player pl : playerQueue) {
            pl.setMoved(false);
            if (pl.isBigBlind()) {
                int tempbb = bbvalue;
                if (pl.getPlayerTokens() < bbvalue) {
                    tempbb = pl.getPlayerTokens();
                }
                pl.setCurrentPlayerBet(tempbb);
                setCurrentPot(getCurrentPot() + tempbb);
                setCurrentBet(tempbb);
                pl.setCurrentBet(tempbb);
                pl.setCurrentTotalBet(pl.getCurrentTotalBet() + tempbb);
                pl.playerState = ActionTaken.BETING;
                pl.setPlayerTokens(pl.getPlayerTokens() - tempbb);
                System.out.println("Gracz " + pl.getPlayerIndex() + " is bg blind ");
                currentPlayer = pl;
                sendDataToEachClient(playerQueue);
            }
            if (pl.isSmallBlind()) {
                int tempsb = sbvalue;
                if (pl.getPlayerTokens() < sbvalue)
                    tempsb = pl.getPlayerTokens();
                pl.setCurrentPlayerBet(tempsb);
                setCurrentPot(getCurrentPot() + tempsb);
                pl.setCurrentBet(tempsb);
                pl.setCurrentTotalBet(pl.getCurrentTotalBet() + tempsb);
                //pl.playerState = ActionTaken.BETING;
                pl.setPlayerTokens(pl.getPlayerTokens() - tempsb);
                System.out.println("Gracz " + pl.getPlayerIndex() + " is small blind ");

            }
        }
    }

    //modify this method when more data is needed on client side
    //the data format is: x;y;z
    // ';' as a default delimiter
    //package contains: "data", current player's name, current bet,
    //current pot, each player's (left in game) name and tokens
    //0-data,1-CPname,2-CPtokens,3-PPaction,4-moves_counter,5-bet,6-pot,7-...
    public String createDataPackage(List<Player> players) {
        String data;
        data = "data";
        data += ";" + getCurrentPlayer().getPlayerName();
        data += ";" + getCurrentPlayer().getPlayerTokens();
        if (getCurrentPlayer().isBigBlind() && getRoundNumber() == 0 && getCurrentBet() > getCurrentPlayer().getCurrentBet())
            data += ";" + "BETING";
        else if (getCurrentPlayer().isBigBlind() && getRoundNumber() == 0 && getCurrentBet() <= getCurrentPlayer().getCurrentBet())
            data += ";" + "BB";
        else
            data += ";" + ppaction;

        data += ";" + movesCounter;
        data += ";" + getCurrentBet();
        data += ";" + getCurrentPot();

        for (Player pl : playersInRound) {
            data += ";" + pl.getPlayerName();
            if (pl.isBigBlind())
                data += ";" + pl.getPlayerTokens() + " (BB)";
            else if (pl.isSmallBlind())
                data += ";" + pl.getPlayerTokens() + " (SB)";
            else
                data += ";" + pl.getPlayerTokens();
        }
        return data;
    }

    public void startAuction(int round) {
        System.out.println("Start aukcji " + round);
        setRoundNumber(round);
        endOfAuction = false;
        if (round != 0)
            setPlayerQueue(playersInRound); //set player queue with players in round
        else {
            setInitialPlayerQueue(playersInRound);
            for (Player plallin : playerQueue) {
                plallin.actionName = null;
                plallin.setActionName(null);
            }
        }


        if (round == 0)
            placeBlindsOnTable();


        ListIterator<Player> itr;// = playerQueue.listIterator();
        while (!endOfAuction) { //while everyone makes his move and all player's bets are equal
            itr = playerQueue.listIterator();
            while (itr.hasNext()) {
                //for(Player player : playerQueue){


                Player player = itr.next();
                currentPlayer = player;

                if (checkPlayers()) {
                    endOfAuction = true;
                    continue;
                }
                if (!currentPlayer.isInGame() || !player.isInRound())
                    continue;

                //set previous player
                // change couse it may couse some bugs when previous player was removed
                if (playerQueue.indexOf(currentPlayer) == 0)
                    previousPlayer = playerQueue.get(playerQueue.size() - 1);
                else {
                    int index = playerQueue.indexOf(currentPlayer);

                    previousPlayer = playerQueue.get(index - 1);
                }

                ppaction = previousPlayer.playerState;

                sendDataToEachClient(playerQueue);
                sendHandInfoToEachClient(playerQueue);

                if (currentPlayer.playerState != ActionTaken.ALLIN)
                    currentPlayer.setActive(createDataPackage(playerQueue), round);

                difference = getCurrentBet() - player.getCurrentBet();
                try {
                    switch (currentPlayer.getActionName()) {
                        case "check":
                            currentPlayer.check();
                            break;
                        case "call":
                            currentPlayer.call(difference);
                            break;
                        case "bet":
                            currentPlayer.bet(currentPlayer.getCurrentBet());
                            break;
                        case "raise":
                            currentPlayer.raise(currentPlayer.getCurrentBet());
                            break;
                        case "fold":
                            currentPlayer.fold();
                            break;
                        case "allin":
                            currentPlayer.allIn();
                            break;
                        default:
                            break;
                    }
                } catch (NullPointerException e) {
//					currentPlayer.setBlocked();
                    currentPlayer.setActionName("fold");
                    currentPlayer.playerState = ActionTaken.FOLDING;
                    currentPlayer.setInGame(false);
                    currentPlayer.setInRound(false);
//					currentPlayer.setPlayerTokens(0);
//					currentPlayer.setPlayerName("Player left!");
                }
                //wykaszam getcurrenttotalbet >> getcurrentbet

                if (player.playerState == ActionTaken.CHECKING) {
                    //do nothing important
                }
                if (player.playerState == ActionTaken.BETING) {
                    setCurrentBet(player.getCurrentBet());
                    setCurrentPot(getCurrentPot() + player.getCurrentBet());
                }
                if (player.playerState == ActionTaken.CALLING) {
                    setCurrentPot(getCurrentPot() + difference);
                }
                if (player.playerState == ActionTaken.RISING) {
                    setCurrentBet(player.getCurrentBet());
                    setCurrentPot(getCurrentPot() - player.getCurrentPlayerBet() + player.getCurrentBet());
                }
                if (player.playerState == ActionTaken.FOLDING) {
                    //itr.remove();
                    player.setInRound(false);
                    player.setInGame(false); // for needs of game
                }
                if (player.playerState == ActionTaken.ALLIN) {
                    setCurrentPot(getCurrentPot() + player.getCurrentBet());
                    if (player.getCurrentBet() > getCurrentBet())
                        setCurrentBet(player.getCurrentBet());
                    player.setPlayerTokens(0);

                    //it.remove();
                    player.setInRound(false);
                }


                currentPlayer.setMoved(true);
                currentPlayer.setBlocked();
                movesCounter++;


                //for(Player pl : playerQueue)
                //	System.out.print(pl.getCurrentBet() + " ");
                //System.out.println();
                sendDataToEachClient(playerQueue);
                if (movesCounter >= allMoved() && checkIfBetsAreEqual(playerQueue)) {    //if everyone took his turn and all player's bets are equal
                    endOfAuction = true;
                    break;
                }
                if (playerQueue.size() <= 1) {
                    endOfAuction = true;
                    break;
                }
            }

            //rewrite iterator to playerQueue
            while (itr.hasPrevious()) {
                itr.previous();
            }

            playerQueue = new ArrayList<>();

            while (itr.hasNext()) {
                playerQueue.add(itr.next());
            }

        }
        if (endOfAuction)
            System.out.println("Koniec aukcji!");
        movesCounter = 0;
        ppaction = null;
        //reseting values before next auction
        //*current bet
        setCurrentBet(0);
        //*player state and his bet for each player left in round
        for (Player player : playerQueue) {
            if (player.playerState != ActionTaken.ALLIN)
                player.playerState = null;

            player.setCurrentBet(0);
            player.setCurrentPlayerBet(0);
        }
        //*player queue
        playerQueue = new ArrayList<Player>();
        //current pot should be reset in round, after saving it's value
    }


////ACTUAL METHOD FOR STARTING AUCTION/////////////////////////////////////////

    private int allMoved() {
        int counter = 0;
        for (Player player : playerQueue) {
            if (player.isInRound() && player.isMoved()) {
                counter++;
            }
        }
        return counter;
    }

////GETTERS AND SETTERS////

    private boolean checkPlayers() {
        int counter = 0;

        for (Player player : playerQueue) {
            if (player.isInGame() && player.isInRound()) {
                ++counter;
            }
        }

        return counter == 1;
    }

    public int getCurrentPot() {
        return currentPot;
    }

    public void setCurrentPot(int currentPot) {
        this.currentPot = currentPot;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public int getRaiseValue() {
        return raiseValue;
    }

    public void setRaiseValue(int raiseValue) {
        this.raiseValue = raiseValue;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public List<Player> getPlayersInRound() {
        return playersInRound;
    }

    public void setPlayersInRound(List<Player> playersInGame) {
        this.playersInRound = playersInGame;
    }

    public List<Player> getPlayerQueue() {
        return playerQueue;
    }

    public void setPlayerQueue(List<Player> players) {
        boolean playerAfterSmallBlind = false;
        int firstAddedPlayer = 0;
        int index = 0;
        for (Player player : players) {
            if (player.isSmallBlind()) {
                playerQueue.add(player);
                firstAddedPlayer = index;
                playerAfterSmallBlind = true;
            } else if (playerAfterSmallBlind) {
                playerQueue.add(player);
            }
            index++;
        }
        for (int i = 0; i < firstAddedPlayer; i++) {
            playerQueue.add(players.get(i));
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Player getPreviousPlayer() {
        return previousPlayer;
    }

    public void setPreviousPlayer(Player previousPlayer) {
        this.previousPlayer = previousPlayer;
    }

}
