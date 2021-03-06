package com.betherichest.android.gameElements.achievement;

import com.betherichest.android.App;
import com.betherichest.android.R;

import java.text.NumberFormat;
import java.util.Locale;

public class TapAchievement extends Achievement {
    int requiredClick;

    public TapAchievement(String name, int requiredClick, Object reward) {
        super(name, "Tap on the dollar " + NumberFormat.getInstance(Locale.FRANCE).format(requiredClick) + " times", reward, App.convertThousandsToSIUnit(requiredClick, true) + "x");
        this.imageResource = R.drawable.click;
        this.requiredClick = requiredClick;
    }

    public int getRequiredClick() {
        return requiredClick;
    }
}
