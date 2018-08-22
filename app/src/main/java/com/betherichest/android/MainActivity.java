package com.betherichest.android;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Game game = Game.getInstance();
    private GUIManager guiManager;
    private Communicator comm;
    private FragmentManager manager = getSupportFragmentManager();
    private AppDatabase appDatabase;

    public MainActivity() {
        comm = new Communicator("krisz094.asuscomm.com");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
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
        //game.loadInvestments(appDatabase.investmentDao().getInvestments());

        guiManager = new GUIManager(this.findViewById(android.R.id.content), getApplicationContext(), getWindowManager(), getSupportActionBar());
        guiManager.setMainUITexts();
    }

    public void investmentsIconClick(View view) {
        guiManager.openFragment(manager, InvestmentListFragment.class.getName(), R.id.investment_list_container, new InvestmentListFragment());
    }

    public void upgradesIconClick(View view) {
        if (game.getDisplayableUpgrades().size() == 0) {
            guiManager.showNoUpgradeToast();
        } else {
            guiManager.openFragment(manager, UpgradeListFragment.class.getName(), R.id.upgrade_list_container, new UpgradeListFragment());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        comm.GETEndpoint("lognew");
    }

    @Override
    protected void onResume() {
        super.onResume();
        comm.GETEndpoint("lognew");
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveState();
    }

    private void saveState() {
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
