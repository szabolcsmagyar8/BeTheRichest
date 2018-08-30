package com.betherichest.android.GameElements;

public class Gambling extends GameElement {
    private static int currentId = 0;

    private int minWinAmount;
    private int maxWinAmount;

    private double chance;

    public Gambling(String name, double price, int minWinAmount, int maxWinAmount, double chance, int imageResource) {
        this.id = currentId++;
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
        this.minWinAmount = minWinAmount;
        this.maxWinAmount = maxWinAmount;
        this.chance = chance;
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
