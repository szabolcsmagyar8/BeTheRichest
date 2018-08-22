package com.betherichest.android;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szabi on 2018. 03. 14..
 */

@Entity
public class Investment extends GameElement {

    @PrimaryKey
    static int currentId = 0;

    @ColumnInfo(name = "rank")
    private int rank = 0;

    @Ignore
    private double basePrice;

    @Ignore
    private double baseDpS;

    @Ignore
    private final double coefficient = 1.15;

    @Ignore
    private int[] upgradeEffectMultipliers;

    @Ignore
    private List<Upgrade> relevantUpgrades = new ArrayList<>();

    @Ignore
    private List<Upgrade> purchasedRelevantUpgrades = new ArrayList<>();

    public Investment(String name, double basePrice, double baseDpS, String description, int imageResource, int[] upgradeEffectMultipliers) {
        this.id = currentId++;
        this.name = name;
        this.basePrice = basePrice;
        this.baseDpS = baseDpS;
        this.description = description;
        this.imageResource = imageResource;
        this.upgradeEffectMultipliers = upgradeEffectMultipliers;
    }

    public Investment(int id, int rank) {
        this.id = id;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void increaseRank() {
        rank++;
    }

    public long getBasePrice() {
        return Math.round(basePrice);
    }

    @Override
    public double getPrice() {
        return (double) Math.round(basePrice * Math.pow(coefficient, rank));
    }

    public double getMoneyPerSec() {
        double moneyPerSec = rank * baseDpS;
        for (Upgrade upgrade : purchasedRelevantUpgrades) {
            if (upgrade.isPurchased()) {
                moneyPerSec *= upgrade.getMultiplier();
            }
        }
        return moneyPerSec;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int[] getUpgradeEffectMultipliers() {
        return upgradeEffectMultipliers;
    }

    public double getMoneyPerSecPerRank() {
        return rank == 0 ? baseDpS : getMoneyPerSec() / rank;
    }

    @Override
    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= getPrice();
    }

    public List<Upgrade> getRelevantUpgrades() {
        return relevantUpgrades;
    }

    public List<Upgrade> getPurchasedRelevantUpgrades() {
        return purchasedRelevantUpgrades;
    }

    public void addRelevantUpgrade(Upgrade upgrade) {
        relevantUpgrades.add(upgrade);
    }

    public void addPurchasedRelevantUpgrade(Upgrade upgrade) {
        purchasedRelevantUpgrades.add(upgrade);
    }
}
