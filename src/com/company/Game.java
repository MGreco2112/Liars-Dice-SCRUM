package com.company;

import java.util.*;

public class Game {
    public Player activePlayer;
    public Player lastPlayer;
    public int numberOfPlayers;
    public int diceTotal;
    public int currentValueBid = 0;
    public int currentQuantity = 0;
    public HashMap<Integer, Integer> tableDice;
    public ArrayList<Player> players = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public Game() {
        round();
    }

    public void round() {

        playerSetup();

        for (Player player : players) {

            activePlayer = player;

            activePlayer.cup.addDice(diceTotal);

//        while (activePlayer.cup.dice.size() > 0) {

            if (currentValueBid == 0) {
                tableDice = new HashMap<>();
            }


            activePlayer.cup.rollDice();

            for (Die die : activePlayer.cup.dice) {
                if (tableDice.containsKey(die.faceUpValue)) {
                    int count = tableDice.get(die.faceUpValue);
                    count++;
                    tableDice.put(die.faceUpValue, count);
                } else {
                    tableDice.put(die.faceUpValue, 1);
                }
            }

            displayDice();

            if (currentValueBid == 0) {
                initialBid();
            } else {
                System.out.println(activePlayer.name + ", would you like to make a new bet or call the last bet a " +
                        "lie?\n1) New Bet\n2) Call Lie\nCurrent bet: " + currentQuantity + " instances of " + currentValueBid);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        secondBid();
                        break;

                    case 2:
                    callLiar();
                    break;
                }
            }

            lastPlayer = player;

//        }
        }

        gameOver();

    }

    public void playerSetup() {
        System.out.println("How many Players will there be?");
        String playerNumber = scanner.nextLine();
        numberOfPlayers = Integer.parseInt(playerNumber);
        System.out.println("How many dice would you like to roll with?");
//        diceTotal = scanner.nextInt();
        String dice = scanner.nextLine();
        diceTotal = Integer.parseInt(dice);

        while (players.size() < numberOfPlayers) {

            System.out.println("Enter your name:");
            String name = scanner.nextLine();

            players.add(new Player(name));
        }

    }

    private void displayDice() {
        System.out.println(activePlayer.name + " rolls their dice!");

        String output = "";

        for (Die die : activePlayer.cup.dice) {
            output += die.faceUpValue + " ";
        }

        System.out.println(output.trim());
    }

    private void initialBid() {
        System.out.println(activePlayer.name + ", make your initial bid. This will be a Die Value...");

        currentValueBid = scanner.nextInt();

        System.out.println("... and the number of times that value occurs");

        currentQuantity = scanner.nextInt();


    }

    public void secondBid() {
        int newBidValue;
        int newQuantity;
        int choice;


        if (currentValueBid == 6) {

            System.out.println(activePlayer.name + ", since the current bid value is 6, you may only increase the " +
                    "quantity of the bid can be any, but the value must stay at 6.");
            choice = 2;
        } else {
            System.out.println(activePlayer.name + ", make your bid. You may either:\n1) Increase the die " +
                    "value from the last " +
                    "bid\n2) Bid any value of dice at an increased quantity");

            choice = scanner.nextInt();
        }




        switch (choice) {
            case 1 :
                System.out.println("The last bid was a value of " + currentValueBid + ", that occurred " + currentQuantity +
                        " times");

                do {
                    System.out.println("What value would you like to increase the bid die to?\nOld value: " + currentValueBid);
                    newBidValue = scanner.nextInt();
                } while (newBidValue <= currentValueBid);

                System.out.println("The new bid is " + currentQuantity + " instances of " + newBidValue);

                currentValueBid = newBidValue;
                break;

            case 2 :
                do {
                    System.out.println("Which quantity of dice do you wish to bid? This value must be higher than " + currentQuantity);
                    newQuantity = scanner.nextInt();
                } while (newQuantity <= currentQuantity);


                if (currentValueBid != 6) {

                    System.out.println("What die value would you like to bid? This can be of any value");
                    newBidValue = scanner.nextInt();
                } else {
                    System.out.println();
                    newBidValue = 6;
                }


                System.out.println("The new bid is " + newQuantity + " instances of " + newBidValue);

                currentValueBid = newBidValue;
                currentQuantity = newQuantity;
                break;

            default :
                System.out.println("Invalid selection");

        }

    }

    public void callLiar() {

        System.out.println(activePlayer.name + " thinks the last bet is a lie...");

        if (tableDice.containsKey(currentValueBid) && tableDice.get(currentValueBid) >= currentQuantity) {
            System.out.println("The last bid is not a lie! " + activePlayer.name + " loses a die!");
            activePlayer.cup.removeDie();
        } else {
            System.out.println("The current bid is a lie! " + lastPlayer.name + " loses a die!");
            lastPlayer.cup.removeDie();
        }



        currentValueBid = 0;
        currentQuantity = 0;
    }

    private void gameOver() {
        if (players.get(0).cup.dice.size() == players.get(1).cup.dice.size()) {
            System.out.println("The game is a tie!");
        } else {
            System.out.println(players.get(0).cup.dice.size() > players.get(1).cup.dice.size() ? players.get(0).name +
                    " " +
                    "is" +
                    " " +
                    "the " +
                    "winner!" : players.get(1).name + " is the winner!");
        }

        System.out.println("Game Over\nPlay Again?\n(y)es\n(n)o");
        scanner.nextLine();

        String choice = scanner.nextLine();

        switch (choice.toLowerCase(Locale.ROOT)) {
            case "y" :
                round();
                break;

            case "n" :
                System.out.println("Thank you for playing!");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid selection");
                gameOver();
        }
    }
}
