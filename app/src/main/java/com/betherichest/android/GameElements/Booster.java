package com.betherichest.android.GameElements;

public class Booster extends GameElement {
    private static int currentId = 0;

    private int interval;

    public Booster() {
        this.id = currentId++;
    }

    public int getInterval() {
        return interval;
    }
}
