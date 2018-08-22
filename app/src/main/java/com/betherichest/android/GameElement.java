package com.betherichest.android;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

public abstract class GameElement {

    @PrimaryKey
    protected int id;

    @Ignore
    protected String name;

    @Ignore
    protected double price;

    @Ignore
    protected int imageResource;

    @Ignore
    protected String description;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= price;
    }
}
