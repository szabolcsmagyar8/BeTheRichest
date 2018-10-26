package com.betherichest.android.gameElements;

public class TapUpgradeConfig {
    private double price;
    private double clickRequired;
    private double moneyPerTapMultiplier;

    public TapUpgradeConfig(double price, double moneyPerTapMultiplier, double clickRequired) {
        this.price = price;
        this.moneyPerTapMultiplier = moneyPerTapMultiplier;
        this.clickRequired = clickRequired;
    }

    public double getPrice() {
        return price;
    }

    public double getMoneyPerTapMultiplier() {
        return moneyPerTapMultiplier;
    }

    public double getClickRequired() {
        return clickRequired;
    }
}
