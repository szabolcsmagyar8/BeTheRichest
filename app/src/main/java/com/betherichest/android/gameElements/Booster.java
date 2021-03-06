package com.betherichest.android.gameElements;

import com.betherichest.android.mangers.Game;

public class Booster extends GameElement {
    private static int currentId = 0;
    private double interval;
    private String skuId;
    private String title;
    private String priceText;

    public Booster() {
        this.id = currentId++;
    }

    public double getInterval() {
        return interval;
    }

    public String getSkuId() {
        return skuId;
    }

    public double getActualReward() {
        return Game.getInstance().getMoneyPerSec() * Game.SEC_TO_HOUR_MULTIPLIER * interval;
    }

    public String getTitle() {
        return title;
    }

    public String getPriceText() {
        return priceText;
    }
}
