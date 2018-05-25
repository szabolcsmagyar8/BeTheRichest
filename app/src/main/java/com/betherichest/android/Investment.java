package com.betherichest.android;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szabi on 2018. 03. 14..
 */

public class Investment {
    static int currentId = 0;

    private int id;

    private String name;
    private double basePrice;
    private double baseDpS;
    private String description;
    private final double coefficient = 1.15;
    private int rank = 0;
    private int imageResource;

    private int[] upgradeEffectMultipliers;
    private List<Integer> relevantUpgradeIds = new ArrayList<>();

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

    public double getPrice() {
        return (double) Math.round(basePrice * Math.pow(coefficient, getRank()));
    }

    public double getMoneyPerSec() {

        double moneyPerSec = getRank() * baseDpS;
        for (int id: relevantUpgradeIds){
           // moneyPerSec*=
        }
        return getRank() * baseDpS;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int[] getUpgradeEffectMultipliers() {
        return upgradeEffectMultipliers;
    }

    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= getPrice();
    }

    public double getMoneyPerSecPerRank() {
        return baseDpS;
    }

    public void addRelevantUpgradeId(int id) {
        relevantUpgradeIds.add(id);
    }
}
