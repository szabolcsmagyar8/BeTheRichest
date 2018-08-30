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
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();
        dbManager = new DatabaseManager();
        dbManager.loadStateFromDb();

        guiManager = new GUIManager(this.findViewById(android.R.id.content), getWindowManager(), getSupportActionBar(), fragmentManager);
        guiManager.setMainUITexts();
    }

    public static Context getContext() {
        return context;
    }

    public void investmentsIconClick(View view) {
        guiManager.openFragment(R.id.investment_list_container, new InvestmentListFragment());
    }

    public void upgradesIconClick(View view) {
        if (game.getDisplayableUpgrades().size() == 0) {
            guiManager.showNoUpgradeToast();
        } else {
            guiManager.openFragment(R.id.upgrade_list_container, new UpgradeListFragment());
        }
    }

    public void gamblingIconClick(View view) {
        guiManager.openFragment(R.id.gambling_list_container, new GamblingListFragment());
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
