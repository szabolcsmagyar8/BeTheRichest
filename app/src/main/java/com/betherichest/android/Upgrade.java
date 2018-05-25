package com.betherichest.android;

enum UpgradeCategory {
    InvestmentUpgrade, TapUpgrade, GamblingUpgrade,
}

public class Upgrade extends GameElement{
    static int currentId = 0;
    private int multiplierEffect;
    private int color;
    private boolean displayable;
    private boolean purchased = false;

    private Investment relevantInvestment;

    private UpgradeCategory category;

    public Upgrade(String description, double price, int multiplierEffect, int imageResource, int color, UpgradeCategory category, Investment relevantInvestment) {
        this.id = currentId++;
        this.description = description;
        this.price = price;
        this.multiplierEffect = multiplierEffect;
        this.color = color;
        this.imageResource = imageResource;
        this.category = category;
        this.relevantInvestment = relevantInvestment;
    }

    public int getColor() {
        return color;
    }

    public int getMultiplierEffect() {
        return multiplierEffect;
    }

    public UpgradeCategory getCategory() {
        return category;
    }

    public boolean isDisplayable() {
        return !purchased;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public Investment getRelevantInvestment() {
        return relevantInvestment;
    }
}
