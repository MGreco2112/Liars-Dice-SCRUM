package com.company;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public Player activePlayer;
    public int diceTotal;
    public int currentValueBid;
    public int currentQuantity;
    public HashMap<Integer, Integer> tableDice = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public Game() {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("How many dice would you like to roll with?");
        diceTotal = scanner.nextInt();

        activePlayer = new Player(name);
        activePlayer.cup.addDice(diceTotal);
    }

    public void round() {
        activePlayer.cup.rollDice();

        for (Die die : activePlayer.cup.dice) {
            if (tableDice.containsKey(die.faceUpValue)) {
                int count = tableDice.get(die.faceUpValue);
                count ++;
                tableDice.put(die.faceUpValue, count);
            } else {
                tableDice.put(die.faceUpValue, 1);
            }
        }

        displayDice();
        initialBid();
        callLiar();
        secondBid();
        callLiar();
    }

    private void displayDice() {
        System.out.println(activePlayer.name + " rolls their dice!");

        for (Die die : activePlayer.cup.dice) {
            System.out.println(die.faceUpValue);
        }
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
        String choice = "";

        if (currentValueBid != 6) {

            System.out.println(activePlayer.name + ", make your second bid. You may either:\n1) Increase the die value from the last " +
                    "bid\n2) Bid any value of dice at an increased quantity");
                scanner.nextLine();
                choice = scanner.nextLine();
        } else {
            System.out.println(activePlayer.name + ", since the current bid value is 6, you may only increase the " +
                    "quantity and bid any value");
            choice = "2";
        }




        switch (choice) {
            case "1" :
                System.out.println("The last bid was a value of " + currentValueBid + ", that occurred " + currentQuantity +
                        " times");

                do {
                    System.out.println("What value would you like to increase the bid die to?\nOld value: " + currentValueBid);
                    newBidValue = scanner.nextInt();
                } while (newBidValue <= currentValueBid);

                System.out.println("The new bid is " + currentQuantity + " instances of " + newBidValue);

                currentValueBid = newBidValue;
                break;

            case "2" :
                do {
                    System.out.println("Which quantity of dice do you wish to bid? This value must be higher than " + currentQuantity);
                    newQuantity = scanner.nextInt();
                } while (newQuantity <= currentQuantity);

                System.out.println("What die value would you like to bid? This can be of any value");

                newBidValue = scanner.nextInt();


                System.out.println("The new bid is " + newQuantity + " instances of " + newBidValue);

                currentValueBid = newBidValue;
                currentQuantity = newQuantity;
                break;

            default :
                System.out.println("Invalid selection");

        }

    }

    public void callLiar() {
        System.out.println(tableDice);
        System.out.println("Is the current bid, " + currentQuantity + " dice of " + currentValueBid + " value, a " +
                "lie?\n(y)es\n(n)o");

        System.out.println();
        String choice = scanner.nextLine();

        switch (choice.toLowerCase(Locale.ROOT)) {
            case "y" :
                System.out.println(activePlayer.name + " thinks the last bet is a lie...");

                if (tableDice.containsKey(currentValueBid) && tableDice.get(currentValueBid) <= currentQuantity) {
                    System.out.println("The last bid is not a lie! " + activePlayer.name + " loses a die!");
                    activePlayer.cup.removeDie();
                } else {
                    System.out.println("The current bid is a lie!");
                }

                break;

            case "n" :
                System.out.println(activePlayer.name + " thinks the current bid is not a lie.");

                break;

            default:
                System.out.println("Invalid selection, try again!");
                callLiar();
        }
    }
}
