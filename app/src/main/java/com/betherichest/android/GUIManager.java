package com.betherichest.android;

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
}