package com.betherichest.android.gameElements;

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
}
