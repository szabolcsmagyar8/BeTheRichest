package com.betherichest.android.GameElements;

/**
 * Created by Szabi on 2018. 06. 08..
 */

public class InvestmentUpgradeConfig {
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
