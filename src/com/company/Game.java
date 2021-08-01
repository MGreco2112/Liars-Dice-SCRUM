package com.company;

import java.util.Random;
import java.util.Scanner;

public class Game {
    Player activePlayer;
    int diceTotal;
    int currentValueBid;
    int currentQuantity;
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
        displayDice();
        initialBid();
        secondBid();
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
}
