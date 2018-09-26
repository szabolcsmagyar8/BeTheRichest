package com.betherichest.android.GameElements;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.content.res.Resources;

import com.betherichest.android.App;
import com.betherichest.android.Mangers.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szabi on 2018. 03. 14..
 */

@Entity
public class Investment extends GameElement {
    @Ignore
    static int currentId = 0;

    @ColumnInfo(name = "level")
    private int level = 0;

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

    public Investment(int id, int level) {
        this.id = id;
        this.level = level;
    }

    @Ignore
    public Investment(String drawable) {
        int resourceId = Resources.getSystem().getIdentifier(drawable, "drawable", App.getContext().getPackageName());
        this.imageResource = resourceId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void increaseLevel() {
        level++;
    }

    public long getBasePrice() {
        return Math.round(basePrice);
    }

    public double getBaseDpS() {
        return baseDpS;
    }

    @Override
    public double getPrice() {
        return (double) Math.round(basePrice * Math.pow(coefficient, level));
    }

    public double getMoneyPerSec() {
        double moneyPerSec = level * baseDpS;
        for (Upgrade upgrade : purchasedRelevantUpgrades) {
            moneyPerSec *= upgrade.getMultiplier();
        }
        return moneyPerSec;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int[] getUpgradeEffectMultipliers() {
        return upgradeEffectMultipliers;
    }

    public double getMoneyPerSecPerLevel() {
        return level == 0 ? baseDpS : getMoneyPerSec() / level;
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
