package com.betherichest.android.factories;

import com.betherichest.android.gameElements.achievement.Achievement;
import com.betherichest.android.gameElements.achievement.DollarMakerAchievement;
import com.betherichest.android.gameElements.achievement.InvestmentLevelAchievement;
import com.betherichest.android.gameElements.achievement.TapAchievement;
import com.betherichest.android.gameElements.achievement.TimeInGameAchievement;
import com.betherichest.android.gameElements.achievement.TotalUpgradeAchievement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AchievementFactory {
    private static List<Achievement> achievements = new ArrayList<>();


    private static TapAchievement[] tapAchievements = new TapAchievement[]{
            new TapAchievement("Rookie Tapper 10", 10, null),
            new TapAchievement("Rookie Tapper 100", 100, null),
            new TapAchievement("Rookie Tapper 200", 200, null),
            new TapAchievement("Rookie Tapper 500", 500, null),
            new TapAchievement("Rookie Tapper", 1000, null),
            new TapAchievement("Good Tapper", 5000, null),
            new TapAchievement("Serious Tapper", 10000, null),
            new TapAchievement("Heavy Tapper", 20000, null),
            new TapAchievement("Extreme Tapper", 50000, null),
            new TapAchievement("Tapping Master", 100000, null),
            new TapAchievement("Tapping Legend", 200000, null),
            new TapAchievement("Tapping God", 500000, null),
            new TapAchievement("What's up with your fingers??", 1000000, null),
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
            new DollarMakerAchievement("Is there that much money on Earth??", 100000000000000d, null)
    };

    private static TimeInGameAchievement[] timeInGameAchievements = new TimeInGameAchievement[]{
            new TimeInGameAchievement("Fresh Player 0", 0.1, null),     // 1m
            new TimeInGameAchievement("Fresh Player", 0.3, null),     // 3m
            new TimeInGameAchievement("Interested Player", 1, null),          // 10m
            new TimeInGameAchievement("Casual Visitor", 3, null),            // 30m
            new TimeInGameAchievement("Frequent Visitor", 6, null),                // 1h
            new TimeInGameAchievement("Regular Gamer", 12, null),       // 2h
            new TimeInGameAchievement("Serious Gamer", 30, null),          // 5h
            new TimeInGameAchievement("Game Lover", 60, null),            // 10h
            new TimeInGameAchievement("Addict", 120, null),        // 20h
            new TimeInGameAchievement("Sick Gamer", 300, null),      // 50h
            new TimeInGameAchievement("Crazy Addict", 600, null),           // 100h
            new TimeInGameAchievement("Absolute Fanatic", 1200, null),   // 200h
            new TimeInGameAchievement("Do you do anything else??", 3000, null)  // 500h
    };

    private static InvestmentLevelAchievement[] investmentLevelAchievements = new InvestmentLevelAchievement[]{
            new InvestmentLevelAchievement("My First Investment", 1, null),
            new InvestmentLevelAchievement("Trainee", 5, null),
            new InvestmentLevelAchievement("My 10th Investment", 10, null),
            new InvestmentLevelAchievement("Hobby Investor", 20, null),
            new InvestmentLevelAchievement("Investor", 50, null),
            new InvestmentLevelAchievement("Professional Investor", 100, null),
            new InvestmentLevelAchievement("Investment Collector", 200, null),
            new InvestmentLevelAchievement("Insane Investor", 400, null),
            new InvestmentLevelAchievement("Obsessed Collector", 700, null),
            new InvestmentLevelAchievement("Why do you need a thousand investment??", 1000, null),
    };

    private static TotalUpgradeAchievement[] totalUpgradeAchievements = new  TotalUpgradeAchievement[]{
            new TotalUpgradeAchievement("My First Upgrade", 1, null),
            new TotalUpgradeAchievement("Beginner Upgrader", 3, null),
            new TotalUpgradeAchievement("Excited Upgrader", 5, null),
            new TotalUpgradeAchievement("Intermediate Upgrader", 10, null),
            new TotalUpgradeAchievement("My 20th Upgrade", 20, null),
            new TotalUpgradeAchievement("Pro Upgrader", 30, null),
            new TotalUpgradeAchievement("Upgrade Collector", 50, null),
            new TotalUpgradeAchievement("Sick Upgrader", 75, null),
            new TotalUpgradeAchievement("Upgrade Lunatic", 100, null),
            new TotalUpgradeAchievement("Did you buy every single upgrade??", 150, null),
    };


    public static List<Achievement> getCreatedAchievements() {
        createAchievements();
        return achievements;
    }

    private static void createAchievements() {
        achievements.addAll(Arrays.asList(tapAchievements));
        achievements.addAll(Arrays.asList(dollarMakerAchievements));
        achievements.addAll(Arrays.asList(timeInGameAchievements));
        achievements.addAll(Arrays.asList(investmentLevelAchievements));
        achievements.addAll(Arrays.asList(totalUpgradeAchievements));
    }
}
