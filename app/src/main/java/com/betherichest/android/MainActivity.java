package com.betherichest.android;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Game game = Game.Get();
    GUIManager guiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guiManager = new GUIManager(this.findViewById(android.R.id.content));
    }

    public void dollarClick(View view) {
        game.dollarClick();
        guiManager.changeCurrentMoneyText();
    }

    public void investmentClick(View view) {
        game.investmentClick();
        guiManager.setMoneyPerSecText();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        ft.addToBackStack(InvestmentListFragment.class.getName());
        ft.replace(R.id.investment_list_container, new InvestmentListFragment());
        ft.commit();

        //setDollarMargin(0);
    }
}
