package com.betherichest.android.mangers;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.betherichest.android.App;
import com.betherichest.android.StatType;
import com.betherichest.android.activities.AchievementNotificationActivity;
import com.betherichest.android.gameElements.achievement.Achievement;
import com.betherichest.android.gameElements.achievement.DollarMakerAchievement;
import com.betherichest.android.gameElements.achievement.InvestmentLevelAchievement;
import com.betherichest.android.gameElements.achievement.TapAchievement;
import com.betherichest.android.gameElements.achievement.TimeInGameAchievement;
import com.betherichest.android.gameElements.achievement.TotalUpgradeAchievement;
import com.betherichest.android.listenerInterfaces.AdapterRefreshListener;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AchievementManager implements Observer {
    private List<Achievement> achievements;
    private StatisticsManager statManager;
    public static AdapterRefreshListener refreshListener;

    public AchievementManager(List<Achievement> achievements) {
        this.achievements = achievements;
        statManager = Game.statisticsManager;
        statManager.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object value) {
        for (Achievement achievement : achievements) {
            if (!achievement.isUnlocked()) {
                if (value == StatType.TOTAL_CLICKS && achievement instanceof TapAchievement) {
                    if (statManager.getStatByType(StatType.TOTAL_CLICKS).getValue() >= ((TapAchievement) achievement).getRequiredClick()) {
                        unlockAchievement(achievement);
                        break;
                    }
                }
                if (value == StatType.TOTAL_MONEY_COLLECTED && achievement instanceof DollarMakerAchievement) {
                    if (statManager.getStatByType(StatType.TOTAL_MONEY_COLLECTED).getValue() >= ((DollarMakerAchievement) achievement).getRequiredTotalMoney()) {
                        unlockAchievement(achievement);
                        break;
                    }
                }
                if (value == StatType.TOTAL_PLAYING_TIME && achievement instanceof TimeInGameAchievement) {
                    if (statManager.getStatByType(StatType.TOTAL_PLAYING_TIME).getValue() >= ((TimeInGameAchievement) achievement).getRequiredTime()) {
                        unlockAchievement(achievement);
                        break;
                    }
                }
                if (value == StatType.TOTAL_INVESTMENT_LEVELS && achievement instanceof InvestmentLevelAchievement) {
                    if (statManager.getStatByType(StatType.TOTAL_INVESTMENT_LEVELS).getValue() >= ((InvestmentLevelAchievement) achievement).getRequiredInvestmentLevel()) {
                        unlockAchievement(achievement);
                        break;
                    }
                }
                if (value == StatType.UPGRADES_BOUGHT && achievement instanceof TotalUpgradeAchievement) {
                    if (statManager.getStatByType(StatType.UPGRADES_BOUGHT).getValue() >= ((TotalUpgradeAchievement) achievement).getRequiredUpgradeNum()) {
                        unlockAchievement(achievement);
                        break;
                    }
                }
            }
        }
    }

    private void unlockAchievement(Achievement achievement) {
        achievement.unLock();
        Game.statisticsManager.getStatByType(StatType.UNLOCKED_ACHIEVEMENTS).increaseValueByOne();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (refreshListener != null) {
                    refreshListener.refreshAdapter();
                }
            }
        });
        Intent intent = new Intent(App.getContext(), AchievementNotificationActivity.class);
        intent.putExtra("achievement", achievement);
        App.getContext().startActivity(intent);
    }
}
