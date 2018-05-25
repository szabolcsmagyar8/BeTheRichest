package com.betherichest.android;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szabi on 2018. 03. 14..
 */

public class Investment extends GameElement {
    static int currentId = 0;

    private double basePrice;
    private double baseDpS;

    private final double coefficient = 1.15;
    private int rank = 0;

    private int[] upgradeEffectMultipliers;
    private List<Upgrade> relevantUpgrades = new ArrayList<>();
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
                moneyPerSec *= upgrade.getMultiplierEffect();
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

    public List<Upgrade> getPurchasedRelevantUpgrades() {
        return purchasedRelevantUpgrades;
    }

    public void addRelevantUpgrade(Upgrade upgrade) {
        relevantUpgrades.add(upgrade);
    }

    public void addPurchaseRelevantUpgrade(Upgrade upgrade) {
        purchasedRelevantUpgrades.add(upgrade);
    }
}
