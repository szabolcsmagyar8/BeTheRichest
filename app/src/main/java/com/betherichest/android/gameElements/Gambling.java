package com.betherichest.android.gameElements;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.betherichest.android.mangers.Game;

@Entity
public class Gambling extends GameElement {
    @Ignore
    private static int currentId = 0;
    @ColumnInfo
    private double minWinAmount;
    @ColumnInfo
    private double maxWinAmount;
    @ColumnInfo
    private double price;
    @Ignore
    private double chance;

    @Ignore
    public Gambling() {
        this.id = currentId++;
    }

    public Gambling(int id, double minWinAmount, double maxWinAmount, double price) {
        this.id = id;
        this.maxWinAmount = maxWinAmount;
        this.minWinAmount = minWinAmount;
        this.price = price;
    }

    public double getMinWinAmount() {
        return minWinAmount;
    }

    public void setMinWinAmount(double minWinAmount) {
        this.minWinAmount = minWinAmount;
    }

    public double getChance() {
        return chance;
    }

    public double getMaxWinAmount() {
        return maxWinAmount;
    }

    public void setMaxWinAmount(double maxWinAmount) {
        this.maxWinAmount = maxWinAmount;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= price;
    }
}
