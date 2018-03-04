package com.betherichest.android;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Szabi on 2018.03.04..
 */

public class GUIManager {
    View view;
    Game game = Game.Get();

    TextView currentMoneyText;
    TextView moneyPerSecText;
    TextView moneyPerTapText;
    ImageView dollarImage;

    public GUIManager(View view) {
        this.view = view;

        currentMoneyText = view.findViewById(R.id.currentMoneyText);
        moneyPerSecText = view.findViewById(R.id.moneyPerSecText);
        moneyPerTapText = view.findViewById(R.id.moneyPerTapText);
        dollarImage = view.findViewById(R.id.dollar);
    }

    public void changeCurrentMoneyText() {
        currentMoneyText.setText(game.getCurrentMoneyAsString());
    }

    public void setMoneyPerSecText() {
        moneyPerSecText.setText(game.getMoneyPerSecAsString());
    }
}
