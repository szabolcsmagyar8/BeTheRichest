package com.betherichest.android.gameElements.achievement;

import com.betherichest.android.App;
import com.betherichest.android.R;

import java.text.NumberFormat;
import java.util.Locale;

public class DollarMakerAchievement extends Achievement {
    double requiredTotalMoney;

    public DollarMakerAchievement(String name, double requiredTotalMoney, Object reward) {
        super(name, "Make " + NumberFormat.getInstance(Locale.FRANCE).format(requiredTotalMoney) + " dollars total", reward, App.convertThousandsToSIUnit(requiredTotalMoney, true) + " $");
        this.imageResource = R.drawable.dollarsmall;
        this.requiredTotalMoney = requiredTotalMoney;
    }

    public double getRequiredTotalMoney() {
        return requiredTotalMoney;
    }
}
