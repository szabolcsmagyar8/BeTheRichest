package com.betherichest.android.gameElements;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.betherichest.android.gameElements.upgrade.Upgrade;
import com.betherichest.android.mangers.Game;

import java.util.ArrayList;
import java.util.List;

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
    @Ignore
    private boolean locked;

    public Investment(int id, int level) {
        this.id = id;
        this.level = level;
    }

    @Ignore
    public Investment() {
        this.id = currentId++;
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

    public boolean isLocked() {
        List<Investment> invs = Game.getInstance().getInvestments();
        return id - 1 >= 0 && invs.get(id - 1).getLevel() == 0;
    }

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

    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= getPrice() && !isLocked();
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
