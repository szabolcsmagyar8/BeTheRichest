package com.betherichest.android.gameElements.upgrade;

public class GamblingUpgradeConfig {
    private double price;
    private double gamblingMoneyMultiplier;

    public GamblingUpgradeConfig(double price, double gamblingMoneyMultiplier) {
        this.price = price;
        this.gamblingMoneyMultiplier = gamblingMoneyMultiplier;
    }

    public double getPrice() {
        return price;
    }

    public double getGamblingMoneyMultiplier() {
        return gamblingMoneyMultiplier;
    }
}
