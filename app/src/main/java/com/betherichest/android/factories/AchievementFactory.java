package com.betherichest.android.factories;

import com.betherichest.android.gameElements.Achievement;
import com.betherichest.android.gameElements.DollarMakerAchievement;
import com.betherichest.android.gameElements.TapAchievement;

import java.util.ArrayList;
import java.util.List;

public class AchievementFactory {
    private static List<Achievement> achievements = new ArrayList<>();

    private static TapAchievement[] tapAchievements = new TapAchievement[]{
            new TapAchievement("Rookie Tapper 10", 10, null),
            new TapAchievement("Rookie Tapper 50", 50, null),
            new TapAchievement("Rookie Tapper 100", 100, null),
            new TapAchievement("Rookie Tapper 200", 200, null),
            new TapAchievement("Rookie Tapper 500", 500, null),
            new TapAchievement("Rookie Tapper", 1000, null),
            new TapAchievement("Good Tapper", 5000, null),
            new TapAchievement("Serious Tapper", 10000, null),
            new TapAchievement("Heavy Tapper", 20000, null),
            new TapAchievement("Extreme Tapper", 50000, null),
            new TapAchievement("Tapping Master", 100000, null),
            new TapAchievement("Tapping King", 200000, null),
            new TapAchievement("Tapping Legend", 500000, null),
            new TapAchievement("Tapping God", 1000000, null),
            new TapAchievement("What's up with your fingers?", 5000000, null),
    };

    private static DollarMakerAchievement[] dollarMakerAchievements = new DollarMakerAchievement[]{
            new DollarMakerAchievement("Lucky Lemonade Maker", 1000, null),
            new DollarMakerAchievement("Beginner Merchant", 10000, null),
            new DollarMakerAchievement("Merchant", 100000, null),
            new DollarMakerAchievement("Businessman", 1000000, null),
            new DollarMakerAchievement("Serious Businessman", 10000000, null),
            new DollarMakerAchievement("Multimillionaire", 100000000, null),
            new DollarMakerAchievement("Rags To Riches", 1000000000, null),
            new DollarMakerAchievement("Multi-Billionaire", 10000000000d, null),
            new DollarMakerAchievement("Richest Person Ever", 100000000000d, null),
            new DollarMakerAchievement("Even more rich", 1000000000000d, null),
            new DollarMakerAchievement("Richer Than a Country", 10000000000000d, null),
            new DollarMakerAchievement("The Ultimate Moneymaker", 100000000000000d, null),
    };

    public static List<Achievement> getCreatedAchievements() {
        createClickAchievements();
        return achievements;
    }

    private static void createClickAchievements() {
        for (int i = 0; i < tapAchievements.length; i++) {
            achievements.add(tapAchievements[i]);
        }
        for (int i = 0; i < dollarMakerAchievements.length; i++) {
            achievements.add(dollarMakerAchievements[i]);
        }
    }
}
