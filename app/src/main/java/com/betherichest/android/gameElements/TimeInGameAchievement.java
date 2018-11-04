package com.betherichest.android.gameElements;

import com.betherichest.android.App;
import com.betherichest.android.R;

public class TimeInGameAchievement extends Achievement {
    private static int HOUR_MULITPLIER = 600;
    double requiredTime;

    public TimeInGameAchievement(String name, double requiredTime, Object reward) {
        super(name, "Spend " + App.getPlayingTimeString(requiredTime * HOUR_MULITPLIER) + "in the game", reward);
        this.imageResource = R.drawable.stopwatch;
        this.requiredTime = requiredTime * HOUR_MULITPLIER;
    }

    public double getRequiredTime() {
        return requiredTime;
    }
}
