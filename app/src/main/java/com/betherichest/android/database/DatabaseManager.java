package com.betherichest.android.database;

import com.betherichest.android.App;
import com.betherichest.android.GameState;
import com.betherichest.android.gameElements.Achievement;
import com.betherichest.android.gameElements.GameStatistics;
import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.gameElements.InvestmentUpgrade;
import com.betherichest.android.gameElements.Upgrade;
import com.betherichest.android.mangers.Game;
import com.betherichest.android.mangers.StatisticsManager;

import java.util.List;

public class DatabaseManager {
    public static DatabaseManager instance;
    private Game game;

    private AppDatabase appDatabase;

    public DatabaseManager() {
        game = Game.getInstance();
        if (instance == null) {
            instance = this;
        }
        appDatabase = AppDatabase.getAppDatabase(App.getContext());
    }

    public void loadStateFromDb() {
        List<GameState> states = appDatabase.gameStateDAO().getGameState();

        if (states.size() == 0) {
            appDatabase.gameStateDAO().insertAll(new GameState());
        } else {
            game.setCurrentMoney(states.get(0).getCurrentMoney());
            game.setMoneyPerSec(states.get(0).getMoneyPerSec());
            game.setMoneyPerTap(states.get(0).getMoneyPerTap());
            Game.gameState.setFirstDollarClick(states.get(0).isFirstDollarClick());
            Game.gameState.setMaxCurrentMoney(states.get(0).getMaxCurrentMoney());
        }

        if (appDatabase.investmentDao().getInvestments().size() != 0) {
            loadInvestments(appDatabase.investmentDao().getInvestments());
        }
        if (appDatabase.upgradeDao().getUpgrades().size() != 0) {
            loadUpgrades(appDatabase.upgradeDao().getUpgrades());
        }
        if (appDatabase.gameStatisticsDao().geGameStatistics().size() != 0) {
            loadGameStatistics(appDatabase.gameStatisticsDao().geGameStatistics());
        }
        if (appDatabase.achievementDao().getAchievements().size() != 0) {
           loadAchievements(appDatabase.achievementDao().getAchievements());
        }
    }

    public void saveStateToDb() {
        GameState state = Game.gameState;
        state.setCurrentMoney(game.getCurrentMoney());
        state.setMoneyPerSec(game.getMoneyPerSec());
        state.setMoneyPerTap(game.getMoneyPerTap());

        appDatabase.gameStateDAO().insertAll(state);

        for (Investment inv : game.getInvestments()) {
            appDatabase.investmentDao().insertAll(new Investment(inv.getId(), inv.getLevel()));
        }
        for (Upgrade upgrade : game.getUpgrades()) {
            if (upgrade.isPurchased()) {
                appDatabase.upgradeDao().insertAll(new Upgrade(upgrade.getId()));
            }
        }

        for (GameStatistics stat : Game.statisticsManager.getGameStatistics()) {
            appDatabase.gameStatisticsDao().insertAll(new GameStatistics(stat.getId(), stat.getValue()));
        }

        for (Achievement achievement: game.getAchievements()) {
            appDatabase.achievementDao().insertAll(new Achievement(achievement.getId(), achievement.getDateOfAcquiring(), achievement.isUnlocked()));
        }

    }

    private void loadAchievements(List<Achievement> savedAchievements) {
        for (Achievement savedAchievement : savedAchievements) {
            for (Achievement achievement: game.getAchievements()) {
                if (achievement.getId() == savedAchievement.getId()) {
                    achievement.setUnlocked(savedAchievement.isUnlocked());
                    achievement.setDateOfAcquiring(savedAchievement.getDateOfAcquiring());
                }
            }
        }
    }

    private void loadGameStatistics(List<GameStatistics> savedGameStatistics) {
        for (GameStatistics savedStat : savedGameStatistics) {
            for (GameStatistics stat : StatisticsManager.getInstance().getGameStatistics()) {
                if (stat.getId() == savedStat.getId()) {
                    stat.setValue(savedStat.getValue());
                }
            }
        }
    }

    private void loadUpgrades(List<Upgrade> savedUpgrades) {
        for (Upgrade savedUpgrade : savedUpgrades) {
            for (Upgrade upgrade : game.getUpgrades()) {
                if (upgrade.getId() == savedUpgrade.getId()) {
                    upgrade.setPurchased(true);
                    if (upgrade instanceof InvestmentUpgrade) {
                        Investment inv = ((InvestmentUpgrade) upgrade).getRelevantInvestment();
                        if (!inv.getPurchasedRelevantUpgrades().contains(upgrade)) {
                            inv.addPurchasedRelevantUpgrade(upgrade);
                        }
                    }
                    break;
                }
            }
        }
    }

    private void loadInvestments(List<Investment> savedInvestments) {
        for (Investment investment : game.getInvestments()) {
            for (Investment savedInvestment : savedInvestments) {
                if (investment.getId() == savedInvestment.getId()) {
                    investment.setLevel(savedInvestment.getLevel());
                    break;
                }
            }
        }
    }
}
