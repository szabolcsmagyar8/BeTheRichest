package com.betherichest.android.gameElements.achievement;

import com.betherichest.android.App;
import com.betherichest.android.R;

public class TotalGamblingAchievement extends Achievement {
    private double requiredTotalGambling;

    public TotalGamblingAchievement(String name, double requiredTotalGambling, Object reward) {
        super(name, String.format("Gamble %s times", App.NF.format(requiredTotalGambling)), reward, App.convertThousandsToSIUnit(requiredTotalGambling, true) + "x");
        this.requiredTotalGambling = requiredTotalGambling;
        this.imageResource = R.mipmap.clover;
    }

    public double getRequiredTotalGambling() {
        return requiredTotalGambling;
    }
}
