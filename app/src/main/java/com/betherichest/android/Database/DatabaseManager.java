package com.betherichest.android.Database;

import android.content.Context;

import com.betherichest.android.Game;
import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.GameState;

import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Game game;
    private Context context;
    private AppDatabase appDatabase;

    public DatabaseManager(Context context) {
        this.context = context;
        game = Game.getInstance();
        if (instance == null) {
            instance = this;
        }
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public void loadStateFromDb() {
        List<GameState> states = appDatabase.gameStateDAO().getGameState();
        if (states.size() == 0) {
            appDatabase.gameStateDAO().insertAll(new GameState());
        }
        if (appDatabase.investmentDao().getInvestments().size() != 0) {
            game.loadInvestments(appDatabase.investmentDao().getInvestments());
        }
        game.setCurrentMoney(appDatabase.gameStateDAO().getGameState().get(0).getCurrentMoney());
        game.setMoneyPerSec(appDatabase.gameStateDAO().getGameState().get(0).getMoneyPerSec());
        game.setMoneyPerTap(appDatabase.gameStateDAO().getGameState().get(0).getMoneyPerTap());
    }

    public void saveStateToDb() {
        GameState state = game.getGameState();
        state.setCurrentMoney(game.getCurrentMoney());
        state.setMoneyPerSec(game.getMoneyPerSec());
        state.setMoneyPerTap(game.getMoneyPerTap());

        appDatabase.gameStateDAO().insertAll(state);
        for (Investment inv : game.getInvestments()) {
            appDatabase.investmentDao().insertAll(new Investment(inv.getId(), inv.getRank()));
        }
    }
}
