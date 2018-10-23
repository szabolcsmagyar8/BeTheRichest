package com.betherichest.android.gameElements;

import com.betherichest.android.mangers.Game;

public class Booster extends GameElement {
    private static int currentId = 0;

    private int interval;

    private String skuId;

    public Booster() {
        this.id = currentId++;
    }

    public int getInterval() {
        return interval;
    }

    public String getSkuId() {
        return skuId;
    }

    public double getActualReward() {
        return Game.getInstance().getMoneyPerSec() * Game.SEC_TO_HOUR_MULTIPLIER * interval;
    }
}
