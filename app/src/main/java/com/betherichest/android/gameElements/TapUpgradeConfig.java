package com.betherichest.android.gameElements;

public class TapUpgradeConfig {
    private double price;
    private double moneyPerTapMultiplier;

    public double getPrice() {
        return price;
    }

    public double getMoneyPerTapMultiplier() {
        return moneyPerTapMultiplier;
    }

    public TapUpgradeConfig(double price, double moneyPerTapMultiplier) {
        this.price = price;
        this.moneyPerTapMultiplier = moneyPerTapMultiplier;
    }
}
