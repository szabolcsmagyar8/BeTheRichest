package com.betherichest.android.gameElements.achievement;

import com.betherichest.android.App;
import com.betherichest.android.R;

public class GamblingMoneyAchievement extends Achievement {
    double requiredMoneyFromGambling;

    public GamblingMoneyAchievement(String name, double requiredMoneyFromGambling, Object reward) {
        super(name, String.format("Get %s dollars from gambling", App.NF.format(requiredMoneyFromGambling)), reward, App.convertThousandsToSIUnit(requiredMoneyFromGambling, true) + " $");

        this.requiredMoneyFromGambling = requiredMoneyFromGambling;
        this.imageResource = R.drawable.clovermoney;
    }

    public double getRequiredMoneyFromGambling() {
        return requiredMoneyFromGambling;
    }
}
