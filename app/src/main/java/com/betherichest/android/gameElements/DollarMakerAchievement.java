package com.betherichest.android.gameElements;

import com.betherichest.android.R;

import java.text.NumberFormat;
import java.util.Locale;

public class DollarMakerAchievement extends Achievement {
    public DollarMakerAchievement(String name, double requiredTotalMoney, Object reward) {
        super(name, "Make " + NumberFormat.getInstance(Locale.FRANCE).format(requiredTotalMoney) + " dollars total", reward);
        this.imageResource = R.drawable.dollar;
    }
}
