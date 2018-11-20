package com.betherichest.android.gameElements.upgrade;

public class GamblingUpgradeConfig {
    private double price;
    private double gamblingMoneyMultiplier;
    private int requiredGambling;

    public GamblingUpgradeConfig(double price, double gamblingMoneyMultiplier, int requiredGambling) {
        this.price = price;
        this.gamblingMoneyMultiplier = gamblingMoneyMultiplier;
        this.requiredGambling = requiredGambling;
    }

    public double getPrice() {
        return price;
    }

    public double getGamblingMoneyMultiplier() {
        return gamblingMoneyMultiplier;
    }

    public int getRequiredGambling() {
        return requiredGambling;
    }
}
