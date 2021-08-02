package com.company;

import java.util.Random;

public class Die {
    public int numberOfSides;
    public int faceUpValue = 0;

    public Die(int numberOfSides) {
        this.numberOfSides = numberOfSides;
    }

    public int roll() {
        Random random = new Random();

        faceUpValue = (random.nextInt(numberOfSides) + 1);

        return faceUpValue;
    }
}
