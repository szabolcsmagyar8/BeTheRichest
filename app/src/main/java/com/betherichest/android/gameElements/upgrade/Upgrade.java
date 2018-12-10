package com.betherichest.android.gameElements.upgrade;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.betherichest.android.gameElements.GameElement;
import com.betherichest.android.mangers.Game;

@Entity
public class Upgrade extends GameElement {
    @Ignore
    static int currentId = 0;
    @Ignore
    protected double price;
    @Ignore
    private double multiplier;
    @Ignore
    private int color;
    @Ignore
    protected boolean purchased = false;

    public Upgrade(double price, double multiplier, int color) {
        this.id = currentId++;
        this.price = price;
        this.multiplier = multiplier;
        this.color = color;
    }

    public Upgrade(int id) { // special constructor for database purposes
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public double getMultiplier() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= price;
    }
}
