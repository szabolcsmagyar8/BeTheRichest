package com.betherichest.android.gameElements.achievement;

import com.betherichest.android.R;

public class TotalUpgradeAchievement extends Achievement {
    int requiredUpgradeNum;

    public TotalUpgradeAchievement(String name, int requiredUpgradeNum, Object reward) {
        super(name, "Buy " + requiredUpgradeNum + " upgrades", reward);
        this.imageResource = R.drawable.upgrade;
        this.requiredUpgradeNum = requiredUpgradeNum;
    }

    public int getRequiredUpgradeNum() {
        return requiredUpgradeNum;
    }
}
