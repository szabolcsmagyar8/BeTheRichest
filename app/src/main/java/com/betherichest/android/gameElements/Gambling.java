package com.betherichest.android.gameElements;

public class Gambling extends GameElement {
    private static int currentId = 0;

    private double minWinAmount;
    private double maxWinAmount;
    private double chance;

    public Gambling() {
        this.id = currentId++;
    }

    public double getMinWinAmount() {
        return minWinAmount;
    }

    public void setMinWinAmount(double minWinAmount) {
        this.minWinAmount = minWinAmount;
    }

    public double getChance() {
        return chance;
    }

    public double getMaxWinAmount() {
        return maxWinAmount;
    }

    public void setMaxWinAmount(double maxWinAmount) {
        this.maxWinAmount = maxWinAmount;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
