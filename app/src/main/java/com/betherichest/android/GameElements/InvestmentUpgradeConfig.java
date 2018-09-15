package com.betherichest.android.GameElements;

/**
 * Created by Szabi on 2018. 06. 08..
 */

public class InvestmentUpgradeConfig {
    private int basePriceMultiplier;
    private int requiredLevel;

    public int getBasePriceMultiplier() {
        return basePriceMultiplier;
    }

    public InvestmentUpgradeConfig(int basePriceMultiplier, int requiredLevel) {
        this.basePriceMultiplier = basePriceMultiplier;
        this.requiredLevel = requiredLevel;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }
}
