package com.betherichest.android.GameElements;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Upgrade extends GameElement {
    @PrimaryKey
    static int currentId = 0;

    @Ignore
    private int multiplier;

    @Ignore
    private int color;

    @ColumnInfo(name = "rank")
    protected boolean purchased = false;

    public Upgrade(double price, int multiplier, int color) {
        this.id = currentId++;
        this.price = price;
        this.multiplier = multiplier;
        this.color = color;
    }

    public Upgrade(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public boolean isDisplayable() {
        return purchased;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
