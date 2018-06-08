package com.betherichest.android;

/**
 * Created by I345179 on 2018. 06. 08..
 */

public class InvestmentUpgrade extends Upgrade {
    private int requiredRank;

    private Investment relevantInvestment;

    public InvestmentUpgrade(String description, double price, int multiplierEffect, int requiredRank, int imageResource, int color, Investment relevantInvestment) {
        super(description, price, multiplierEffect, imageResource, color);
        this.requiredRank = requiredRank;
        this.relevantInvestment = relevantInvestment;
    }

    @Override
    public boolean isDisplayable() {
        return !purchased && relevantInvestment.getRank() >= requiredRank;
    }

    public Investment getRelevantInvestment() {
        return relevantInvestment;
    }
}
