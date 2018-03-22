package com.betherichest.android;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Szabi on 2018.03.04..
 */

public class GUIManager {
    View view;
    Game game;
    private static GUIManager instance;

    TextView currentMoneyText;
    TextView moneyPerSecText;
    TextView moneyPerTapText;
    ImageView dollarImage;
    ImageView smallDollar;

    public GUIManager(View view) {
        if (instance == null) {
            instance = this;
        }

        game = Game.getInstance();

        this.view = view;

        currentMoneyText = view.findViewById(R.id.currentMoneyText);
        moneyPerSecText = view.findViewById(R.id.moneyPerSecText);
        moneyPerTapText = view.findViewById(R.id.moneyPerTapText);
        dollarImage = view.findViewById(R.id.dollar);
        smallDollar = view.findViewById(R.id.smallDollar);
        smallDollar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                game.cheat();
                return true;
            }
        });

    }

    public void changeCurrentMoneyText() {
        currentMoneyText.setText(game.getCurrentMoneyAsString());
    }

    public void setMainUITexts() {
        currentMoneyText.setText(game.getCurrentMoneyAsString());
        moneyPerSecText.setText(game.getMoneyPerSecAsString());
        moneyPerTapText.setText(game.getMoneyPerTapAsString());
    }

    public void onTotalMoneyChanged(String currentMoneyAsString) {
        currentMoneyText.setText(currentMoneyAsString);
    }

    public static GUIManager getInstance() {
        return instance;
    }

    public void openFragment(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(InvestmentListFragment.class.getName());
        ft.replace(R.id.investment_list_container, new InvestmentListFragment());
        ft.commit();
    }
}