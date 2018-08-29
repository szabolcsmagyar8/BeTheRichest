package com.betherichest.android;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.betherichest.android.Database.DatabaseManager;
import com.betherichest.android.Services.Communicator;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    private Game game = Game.getInstance();
    private GUIManager guiManager;
    private Communicator comm;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();
        dbManager = new DatabaseManager();
        dbManager.loadStateFromDb();

        guiManager = new GUIManager(this.findViewById(android.R.id.content), getWindowManager(), getSupportActionBar());
        guiManager.setMainUITexts();
    }

    public static Context getContext() {
        return context;
    }

    public void investmentsIconClick(View view) {
        guiManager.openFragment(fragmentManager, InvestmentListFragment.class.getName(), R.id.investment_list_container, new InvestmentListFragment());
    }

    public void upgradesIconClick(View view) {
        if (game.getDisplayableUpgrades().size() == 0) {
            guiManager.showNoUpgradeToast();
        } else {
            guiManager.openFragment(fragmentManager, UpgradeListFragment.class.getName(), R.id.upgrade_list_container, new UpgradeListFragment());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.setTimerPaused(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.setTimerPaused(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbManager.saveStateToDb();
    }
}
