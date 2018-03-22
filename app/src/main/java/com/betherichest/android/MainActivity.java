package com.betherichest.android;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Game game = Game.getInstance();
    GUIManager guiManager;

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
        game.investmentsClick();
        openInvestmentList();
    }

    private void openInvestmentList() {
        FragmentManager manager = getSupportFragmentManager();
        guiManager.openFragment(manager);


    }
}
