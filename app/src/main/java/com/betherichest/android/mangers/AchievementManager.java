package com.betherichest.android.mangers;

import com.betherichest.android.gameElements.Achievement;
import com.betherichest.android.gameElements.TapAchievement;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AchievementManager implements Observer {
    List<Achievement> achievements;

    public AchievementManager(List<Achievement> achievements) {
        this.achievements = achievements;
        StatisticsManager statisticsManager = StatisticsManager.getInstance();
        statisticsManager.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object value) {
        for (Achievement achievement : achievements){
            if (!achievement.isUnlocked() && achievement instanceof TapAchievement){
                if ((double)value > ((TapAchievement)achievement).getRequiredClick()){
                    unlockAchievement(achievement);
                }
            }
        }
    }

    private void unlockAchievement(Achievement achievement) {
        achievement.unLock();
        GUIManager.getInstance().displayAchievementNotification();
    }
}
