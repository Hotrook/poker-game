package com.hotrook;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Client {

    private final static int SOCKET_NUMBER = 8901;
    private static Socket soc;
    private static String ipAdress;
    private static AuctionGUI gui;
    private static String playerName;
    private static BufferedReader in;
    private static PrintWriter out;
    private static GameType gametype;
    private static int limit;


    private static int currentBet = 0;
    private static int playerTokens = 0;
    private static int currentPot = 0;
    private static String[] separatedInput;

    public static void main(String[] args) {

        //create client's GUI, disabled until game starts
        gui = new AuctionGUI();

        getIPAdress();
        getPlayerName();

        try {
            //create tools for network communication: socket, reader and writer
            soc = new Socket(ipAdress, SOCKET_NUMBER);
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            out = new PrintWriter(soc.getOutputStream(), true);


            //Send your nickname after successful connection
            out.println(playerName);


            //set the gui visible, but disabled
            gui.setVisible(true);
            //MoveRestrictions.RestrictAll(gui);


            //main game loop
            startGame();
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }

    //show dialog collecting IP address of a server
    private static void getIPAdress() {
        ipAdress = JOptionPane.showInputDialog(
                gui,
                "Enter IP Address of the Server:",
                JOptionPane.QUESTION_MESSAGE);
    }

    //show dialog collecting player's name
    private static void getPlayerName() {
        playerName = JOptionPane.showInputDialog(
                gui,
                "Enter your nickname seen by other players:",
                JOptionPane.QUESTION_MESSAGE);
    }

    private static void startGame() {
        try {
            while (true) {
                separatedInput = parseData(in.readLine());

                if (separatedInput[0].equals("set active")) {
                    MoveRestrictions.resetRestrictions(gui);
                    MoveRestrictions.restrict(gui,
                            Integer.parseInt(separatedInput[3]),
                            Integer.parseInt(separatedInput[6]),
                            separatedInput[4].equals("null") ? ActionTaken.NONE : ActionTaken.valueOf(separatedInput[4]),
                            Integer.parseInt(separatedInput[5]));
                    getMovementFromButton();
                }

                if (separatedInput[0].equals("set blocked")) {
                    MoveRestrictions.restrictAll(gui);
                }

                if (separatedInput[0].equals("data")) {
                    displayData(separatedInput);
                }

                if (separatedInput[0].equals("hand")) {
                    displayPlayerHand(separatedInput);
                }

                if (separatedInput[0].equals("table cards")) {
                    displayTableCards(separatedInput);
                }

                if (separatedInput[0].equals("win")) {
                    JOptionPane.showMessageDialog(gui, "Game has ended, becouse there were no players left. You win!");
                    System.exit(0);
                }
                if (separatedInput[0].equals("lose")) {
                    JOptionPane.showMessageDialog(gui, "You lose, because you have no tokens left!");
                    System.exit(0);
                }
                if (separatedInput[0].equals("game type")) {
                    setGametype(GameType.valueOf(separatedInput[1]));
                    if (gametype.equals(GameType.FIXLIMIT))
                        limit = Integer.parseInt(separatedInput[2]);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, ex.getMessage());
        }
    }


    public static String[] parseData(String input) {
        return input.split(";");
    }


    private static void displayData(String[] data) {
        gui.turn.setText("Ruch gracza: " + data[1]);
        gui.txtStawka.setText(data[5]);
        currentBet = Integer.parseInt(data[5]);
        gui.txtPula.setText(data[6]);
        currentPot = Integer.parseInt(data[6]);
        //players start with index 7
        //7-player1, 8-player1's tokens, 9-player2, ...
        int i = 7;
        for (JTextArea plInfo : gui.players) {
            if (i <= data.length - 1)
                plInfo.setText(data[i] + '\n' + data[i + 1]);
            else
                plInfo.setText("EMPTY CHAIR");
            i += 2;
        }

        for (int counter = 7; counter <= data.length; counter++) {
            if (data[counter].equals(playerName) && data[counter + 1].endsWith(")") == true) {
                gui.playerTokens.setText(data[counter + 1].substring(0, data[counter + 1].length() - 5));
                playerTokens = Integer.parseInt(data[counter + 1].substring(0, data[counter + 1].length() - 5));
                break;
            } else if (data[counter].equals(playerName)) {
                gui.playerTokens.setText(data[counter + 1]);
                playerTokens = Integer.parseInt(data[counter + 1]);
                break;
            }
        }
        if (data[1].equals(playerName))
            gui.playerTokens.setText(data[2]);
    }


    private static void displayPlayerHand(String[] cards) {
        gui.playerCard1.setText(determineRank(cards[1]) + determineSuit(cards[2]));
        gui.playerCard2.setText(determineRank(cards[3]) + determineSuit(cards[4]));
    }


    private static void displayTableCards(String[] cards) {
        gui.card1.setText(determineRank(cards[1]) + determineSuit(cards[2]));
        gui.card2.setText(determineRank(cards[3]) + determineSuit(cards[4]));
        gui.card3.setText(determineRank(cards[5]) + determineSuit(cards[6]));

        if (cards.length == 7) {
            gui.card4.setText(null);
            gui.card5.setText(null);
        } else if (cards.length == 9) {
            gui.card4.setText(determineRank(cards[7]) + determineSuit(cards[8]));
            gui.card5.setText(null);
        } else if (cards.length == 11) {
            gui.card4.setText(determineRank(cards[7]) + determineSuit(cards[8]));
            gui.card5.setText(determineRank(cards[9]) + determineSuit(cards[10]));
        }
    }


    private static String determineRank(String rank) {
        String temp = null;
        switch (rank) {
            case "0":
                temp = "2";
                break;
            case "1":
                temp = "3";
                break;
            case "2":
                temp = "4";
                break;
            case "3":
                temp = "5";
                break;
            case "4":
                temp = "6";
                break;
            case "5":
                temp = "7";
                break;
            case "6":
                temp = "8";
                break;
            case "7":
                temp = "9";
                break;
            case "8":
                temp = "10";
                break;
            case "9":
                temp = "J";
                break;
            case "10":
                temp = "Q";
                break;
            case "11":
                temp = "K";
                break;
            case "12":
                temp = "A";
                break;
            case " ":
                temp = " ";
                break;
        }

        return temp;
    }


    private static String determineSuit(String suit) {
        String temp = null;
        switch (suit) {
            case "0":
                temp = "\u2663";
                break;
            case "1":
                temp = "\u2666";
                break;
            case "2":
                temp = "\u2665";
                break;
            case "3":
                temp = "\u2660";
                break;
            case " ":
                temp = " ";
                break;
        }

        return temp;
    }

    private static void getMovementFromButton() {
        //wait for player's action
        do {

            try {
                gui.latch.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            gui.latch = new CountDownLatch(1);

        } while (illegalMove(gui));
        //send information about player's move

        if (gui.actionName == "bet" || gui.actionName == "raise") {
            out.println(gui.actionName + ";" + gui.getCurrentPlayerBet());
        } else
            out.println(gui.actionName);
        //reset player's move state
        gui.actionName = null;
        gui.setCurrentPlayerBet(0);
        gui.latch = new CountDownLatch(1);
    }


    private static boolean illegalMove(AuctionGUI gui2) {
        boolean checkMove = true;

        if (gui2.actionName == "bet" && (gui2.getCurrentPlayerBet() >= playerTokens || gui2.getCurrentPlayerBet() < 0))
            checkMove = true;
        else
            checkMove = gui2.actionName == "raise" && (gui2.getCurrentPlayerBet() >= (playerTokens + currentBet) || gui2.getCurrentPlayerBet() < 0);

        if (gametype.equals(GameType.FIXLIMIT) && gui2.getCurrentPlayerBet() > limit && gui2.actionName == "raise")
            checkMove = true;
        if (gametype.equals(GameType.POTLIMIT) && gui2.getCurrentPlayerBet() > currentPot && gui2.actionName == "raise")
            checkMove = true;

        return checkMove;
    }


    public static GameType getGametype() {
        return gametype;
    }


    public static void setGametype(GameType gametype) {
        Client.gametype = gametype;
    }


}
