package com.betherichest.android.gameElements;

public class Gambling extends GameElement {
    private static int currentId = 0;

    private int minWinAmount;
    private int maxWinAmount;
    private double chance;

    public Gambling() {
        this.id = currentId++;
    }

    public int getMinWinAmount() {
        return minWinAmount;
    }

    public int getMaxWinAmount() {
        return maxWinAmount;
    }

    public double getChance() {
        return chance;
    }
}
