package com.betherichest.android.GameElements;

public class InvestmentUpgrade extends Upgrade {
    private int requiredLevel;

    private Investment relevantInvestment;

    public InvestmentUpgrade(double price, int multiplierEffect, int requiredLevel, int imageResource, int color, Investment relevantInvestment) {
        super(price, multiplierEffect, color);
        this.requiredLevel = requiredLevel;
        this.relevantInvestment = relevantInvestment;
        this.imageResource = imageResource;
    }

    @Override
    public boolean isDisplayable() {
        return !purchased && relevantInvestment.getLevel() >= requiredLevel;
    }

    public Investment getRelevantInvestment() {
        return relevantInvestment;
    }
}
