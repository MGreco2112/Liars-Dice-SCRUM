package com.company;

import java.util.ArrayList;

public class Cup {
    ArrayList<Die> dice = new ArrayList<>();


    public void addDice(int numberOfDice) {
        while (dice.size() < numberOfDice) {
            Die die = new Die(6);
            dice.add(die);
        }
    }

    public void rollDice() {
        for (Die die : dice) {
            die.roll();
        }
    }
}
