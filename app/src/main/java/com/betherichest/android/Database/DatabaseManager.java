package com.betherichest.android.Database;

import com.betherichest.android.Mangers.Game;
import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.GameElements.Upgrade;
import com.betherichest.android.GameState;
import com.betherichest.android.GameElements.GameStatistics;
import com.betherichest.android.Activities.MainActivity;

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
        appDatabase = AppDatabase.getAppDatabase(MainActivity.getContext());
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
            game.loadInvestments(appDatabase.investmentDao().getInvestments());
        }
        if (appDatabase.upgradeDao().getUpgrades().size() != 0) {
            game.loadUpgrades(appDatabase.upgradeDao().getUpgrades());
        }
        if (appDatabase.gameStatisticsDao().geGameStatistics().size() != 0) {
            game.loadGameStatistics(appDatabase.gameStatisticsDao().geGameStatistics());
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

    }
}
