package com.betherichest.android.factories;

import com.betherichest.android.gameElements.Achievement;

import java.util.ArrayList;
import java.util.List;

public class AchievementFactory {
    public AchievementFactory() {
        List<Achievement> achievements = new ArrayList<>();

        achievements.add(new Achievement("Easy Tapper", "Tap 100 times", 1000, new ArrayList<Object>()));
    }
}
