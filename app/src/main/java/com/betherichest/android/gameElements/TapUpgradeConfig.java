package com.betherichest.android.gameElements;

/**
 * Created by Szabi on 2018. 06. 08..
 */

public class TapUpgradeConfig {
    private int price;
    private int moneyPerTapMultiplier;

    public int getPrice() {
        return price;
    }

    public int getMoneyPerTapMultiplier() {
        return moneyPerTapMultiplier;
    }

    public TapUpgradeConfig(int price, int moneyPerTapMultiplier) {
        this.price = price;
        this.moneyPerTapMultiplier = moneyPerTapMultiplier;
    }
}
