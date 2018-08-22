package com.betherichest.android.GameElements;

import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.GameElements.Upgrade;

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
