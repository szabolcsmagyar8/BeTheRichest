package com.betherichest.android.mangers;

import com.betherichest.android.StatType;
import com.betherichest.android.gameElements.Achievement;
import com.betherichest.android.gameElements.DollarMakerAchievement;
import com.betherichest.android.gameElements.InvestmentLevelAchievement;
import com.betherichest.android.gameElements.TapAchievement;
import com.betherichest.android.gameElements.TimeInGameAchievement;
import com.betherichest.android.gameElements.TotalUpgradeAchievement;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AchievementManager implements Observer {
    List<Achievement> achievements;
    StatisticsManager statManager;

    public AchievementManager(List<Achievement> achievements) {
        this.achievements = achievements;
        statManager = StatisticsManager.getInstance();
        statManager.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object value) {
        for (Achievement achievement : achievements) {
            if (!achievement.isUnlocked()) {
                if (value == StatType.TOTAL_CLICKS && achievement instanceof TapAchievement) {
                    if (statManager.getStatByType(StatType.TOTAL_CLICKS).getValue() >= ((TapAchievement) achievement).getRequiredClick()) {
                        unlockAchievement(achievement);
                    }
                }
                if (value == StatType.TOTAL_MONEY_COLLECTED && achievement instanceof DollarMakerAchievement) {
                    if (statManager.getStatByType(StatType.TOTAL_MONEY_COLLECTED).getValue() >= ((DollarMakerAchievement) achievement).getRequiredTotalMoney()) {
                        unlockAchievement(achievement);
                    }
                }
                if (value == StatType.TOTAL_PLAYING_TIME && achievement instanceof TimeInGameAchievement) {
                    if (statManager.getStatByType(StatType.TOTAL_PLAYING_TIME).getValue() >= ((TimeInGameAchievement) achievement).getRequiredTime()) {
                        unlockAchievement(achievement);
                    }
                }
                if (value == StatType.TOTAL_INVESTMENT_LEVELS && achievement instanceof InvestmentLevelAchievement) {
                    if (statManager.getStatByType(StatType.TOTAL_INVESTMENT_LEVELS).getValue() >= ((InvestmentLevelAchievement) achievement).getRequiredInvestmentLevel()) {
                        unlockAchievement(achievement);
                    }
                }
                if (value == StatType.UPGRADES_BOUGHT && achievement instanceof TotalUpgradeAchievement) {
                    if (statManager.getStatByType(StatType.UPGRADES_BOUGHT).getValue() >= ((TotalUpgradeAchievement) achievement).getRequiredUpgradeNum()) {
                        unlockAchievement(achievement);
                    }
                }
            }
        }
    }

    private void unlockAchievement(Achievement achievement) {
        achievement.unLock();
        GUIManager.getInstance().displayAchievementNotification(achievement);
    }
}
