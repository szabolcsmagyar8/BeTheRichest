package com.betherichest.android.gameElements.achievement;

import com.betherichest.android.R;

public class InvestmentLevelAchievement extends Achievement {
    int requiredInvestmentLevel;

    public InvestmentLevelAchievement(String name, int requiredInvestmentLevel, Object reward) {
        super(name, "Buy " + requiredInvestmentLevel + " investments", reward);
        this.imageResource = R.drawable.investments;
        this.requiredInvestmentLevel = requiredInvestmentLevel;
    }

    public int getRequiredInvestmentLevel() {
        return requiredInvestmentLevel;
    }
}
