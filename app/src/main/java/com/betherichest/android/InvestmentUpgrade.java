package com.betherichest.android;

/**
 * Created by Szabi on 2018. 06. 08..
 */

class InvestmentUpgradeConfig {
    private int basePriceMultiplier;
    private int requiredRank;

    public int getBasePriceMultiplier() {
        return basePriceMultiplier;
    }

    public int getRequiredRank() {
        return requiredRank;
    }

    public InvestmentUpgradeConfig(int basePriceMultiplier, int requiredRank) {
        this.basePriceMultiplier = basePriceMultiplier;
        this.requiredRank = requiredRank;
    }
}

public class InvestmentUpgrade extends Upgrade {
    private int requiredRank;

    private Investment relevantInvestment;

    public InvestmentUpgrade(double price, int multiplierEffect, int requiredRank, int imageResource, int color, Investment relevantInvestment) {
        super(price, multiplierEffect, color);
        this.requiredRank = requiredRank;
        this.relevantInvestment = relevantInvestment;
        this.imageResource = imageResource;
    }

    @Override
    public boolean isDisplayable() {
        return !purchased && relevantInvestment.getRank() >= requiredRank;
    }

    public Investment getRelevantInvestment() {
        return relevantInvestment;
    }
}
