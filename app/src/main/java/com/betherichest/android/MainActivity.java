package com.betherichest.android;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
        game.setCurrentMoney(appDatabase.gameStateDAO().getCurrentMoney());
        game.setMoneyPerSec(appDatabase.gameStateDAO().getMoneyPerSec());
        //    game.setMoneyPerTap(appDatabase.gameStateDAO().getMoneyPerTap());
        //  game.loadInvestments(appDatabase.gameStateDAO().getInvestments());

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
    protected void onResume() {
        super.onResume();
        comm.GETEndpoint("lognew");
    }

    @Override
    protected void onPause() {
        saveState();
        super.onPause();
        comm.GETEndpoint("lognew");
    }

    @Override
    protected void onStop() {
        saveState();
        super.onStop();
    }

    private void saveState() {
        GameState state = game.getGameState();
        state.setCurrentMoney(game.getCurrentMoney());
        state.setMoneyPerSec(game.getMoneyPerSec());
        state.setMoneyPerTap(game.getMoneyPerTap());
        //   state.setInvestments(game.getInvestments());

        appDatabase.gameStateDAO().insertAll(state);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
    }
}
