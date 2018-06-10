package com.betherichest.android;

import java.util.Map;

/**
 * Created by Szabi on 2018. 06. 08..
 */

class TapUpgradeConfig {
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

class TapUpgrade extends Upgrade {
    public TapUpgrade(double price, int multiplier, int color) {
        super(price, multiplier, color);
        this.imageResource = R.drawable.click;
    }

    @Override
    public boolean isDisplayable() {
        return !purchased;
    }
}
