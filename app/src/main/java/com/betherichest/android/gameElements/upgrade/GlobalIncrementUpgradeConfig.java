package com.betherichest.android.gameElements.upgrade;

public class GlobalIncrementUpgradeConfig {
    private double price;
    private double moneyPerTapMultiplier;

    public double getPrice() {
        return price;
    }

    public double getMoneyPerTapMultiplier() {
        return moneyPerTapMultiplier;
    }

    public GlobalIncrementUpgradeConfig(double price, double moneyPerTapMultiplier) {
        this.price = price;
        this.moneyPerTapMultiplier = moneyPerTapMultiplier;
    }
}
