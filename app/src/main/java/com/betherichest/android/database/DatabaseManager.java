package com.betherichest.android.database;

import com.betherichest.android.App;
import com.betherichest.android.GameState;
import com.betherichest.android.activities.LoginActivity;
import com.betherichest.android.connection.ConnectionManager;
import com.betherichest.android.connection.RequestItem;
import com.betherichest.android.gameElements.Gambling;
import com.betherichest.android.gameElements.GameStatistics;
import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.gameElements.Leader;
import com.betherichest.android.gameElements.achievement.Achievement;
import com.betherichest.android.gameElements.upgrade.GamblingUpgrade;
import com.betherichest.android.gameElements.upgrade.InvestmentUpgrade;
import com.betherichest.android.gameElements.upgrade.Upgrade;
import com.betherichest.android.mangers.Game;

import java.util.LinkedList;
import java.util.List;

public class DatabaseManager {
    public static DatabaseManager instance;
    private Game game = Game.getInstance();

    private AppDatabase appDatabase;

    public DatabaseManager() {
        if (instance == null) {
            instance = this;
        }
        appDatabase = AppDatabase.getAppDatabase(App.getContext());
    }

    public void saveStateToDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GameState state = Game.gameState;
                state.setCurrentMoney(game.getCurrentMoney());
                state.setMoneyPerSec(game.getMoneyPerSec());
                state.setMoneyPerTap(game.getMoneyPerTap());
                state.setBearerToken(LoginActivity.BEARER_TOKEN);
                state.setSoundDisabled(Game.soundDisabled);

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
                for (Achievement achievement : game.getAchievements()) {
                    if (achievement.isUnlocked()) {
                        appDatabase.achievementDao().insertAll(new Achievement(achievement.getId(), achievement.getDateOfAcquiring()));
                    }
                }
                for (Leader leader : game.getLeaders()) {
                    appDatabase.leaderDao().insertAll(new Leader(leader.getId(), leader.getMoney()));
                }
            }
        }).start();
    }

    public void loadStateFromDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<GameState> states = appDatabase.gameStateDAO().getGameState();

                if (states.size() == 0) {
                    appDatabase.gameStateDAO().insertAll(new GameState());
                } else {
                    GameState state = states.get(0);
                    game.setCurrentMoney(state.getCurrentMoney());
                    game.setMoneyPerSec(state.getMoneyPerSec());
                    game.setMoneyPerTap(state.getMoneyPerTap());
                    Game.gameState.setFirstDollarClick(state.isFirstDollarClick());
                    Game.gameState.setMaxCurrentMoney(state.getMaxCurrentMoney());
                    Game.setSoundDisabled(state.getSoundDisabled());
                    LoginActivity.BEARER_TOKEN = state.getBearerToken();
                }

                if (appDatabase.achievementDao().getAchievements().size() != 0) {
                    loadAchievements(appDatabase.achievementDao().getAchievements());
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
                if (appDatabase.leaderDao().getLeaders().size() != 0) {
                    loadLeaders(appDatabase.leaderDao().getLeaders());
                }
                if (appDatabase.requestItemDao().getRequestItems().size() != 0) {
                    loadRequestItems(appDatabase.requestItemDao().getRequestItems());
                }
            }
        }).start();
    }

    private void loadRequestItems(List<RequestItem> savedRequestItems) {
        ConnectionManager.requestItems = new LinkedList<>(savedRequestItems);
    }

    private void loadLeaders(List<Leader> savedLeaders) {
        for (Leader savedLeader : savedLeaders) {
            for (Leader leader : game.getLeaders()) {
                if (leader.getId() == savedLeader.getId()) {
                    leader.setMoney(savedLeader.getMoney());
                    break;
                }
            }
        }
    }

    private void loadAchievements(List<Achievement> savedAchievements) {
        for (Achievement savedAchievement : savedAchievements) {
            for (Achievement achievement : game.getAchievements()) {
                if (achievement.getId() == savedAchievement.getId()) {
                    achievement.setUnlocked(true);
                    achievement.setDateOfAcquiring(savedAchievement.getDateOfAcquiring());
                    break;
                }
            }
        }
    }

    private void loadGameStatistics(List<GameStatistics> savedGameStatistics) {
        for (GameStatistics savedStat : savedGameStatistics) {
            for (GameStatistics stat : Game.statisticsManager.getGameStatistics()) {
                if (stat.getId() == savedStat.getId()) {
                    stat.setValue(savedStat.getValue());
                    break;
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
                            break;
                        }
                    }
                    if (upgrade instanceof GamblingUpgrade) {
                        for (Gambling gambling : game.getGamblings()) {
                            double multiplier = upgrade.getMultiplier();
                            gambling.setMinWinAmount(gambling.getMinWinAmount() * multiplier);
                            gambling.setMaxWinAmount(gambling.getMaxWinAmount() * multiplier);
                            gambling.setPrice(gambling.getPrice() * multiplier);
                        }
                        break;
                    }
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

    public void saveRequestItemToDb(RequestItem requestItem) {
        appDatabase.requestItemDao().insertAll(requestItem);
    }

    public void removeRequestItem(RequestItem requestItem) {
        appDatabase.requestItemDao().deleteRequestItem(requestItem);
    }
}
