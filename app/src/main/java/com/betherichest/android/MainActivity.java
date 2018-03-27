package com.betherichest.android;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Game game = Game.getInstance();
    GUIManager guiManager;
    Communicator comm;
    FragmentManager manager = getSupportFragmentManager();

    public MainActivity() {
        comm = new Communicator("krisz094.asuscomm.com");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guiManager = new GUIManager(this.findViewById(android.R.id.content));
        guiManager.setMainUITexts();
    }

    public void dollarClick(View view) {
        game.dollarClick();
        guiManager.changeCurrentMoneyText();
    }

    public void investmentsClick(View view) {
        guiManager.openFragment(manager, InvestmentListFragment.class.getName(), R.id.investment_list_container, new InvestmentListFragment());
    }

    public void upgradesClick(View view) {
        guiManager.openFragment(manager, UpgradeListFragment.class.getName(), R.id.upgrade_list_container, new UpgradeListFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        comm.GETEndpoint("lognew");
    }

    @Override
    protected void onPause() {
        super.onPause();
        comm.GETEndpoint("lognew");
    }
}
