package com.betherichest.android;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Szabi on 2018.03.04..
 */

public class GUIManager {
    private View view;
    private Game game;
    private static GUIManager instance;
    private Context context;

    private TextView currentMoneyText;
    private TextView moneyPerSecText;
    private TextView moneyPerTapText;
    private ImageView dollarImage;
    private ImageView smallDollar;

    private Toast noAvailableUpgradesToast = null;

    public GUIManager(View view, Context context) {
        this.context = context;
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

        dollarImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                animateDollarClick(event);
                return false;
            }
        });
    }

    public void changeCurrentMoneyText() {
        currentMoneyText.setText(game.getCurrentMoneyAsString());
    }

    public void animateDollarClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            game.dollarClick();
            changeCurrentMoneyText();
            dollarImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shrink));
        }
    }

    public void setMainUITexts() {
        currentMoneyText.setText(game.getCurrentMoneyAsString());
        moneyPerSecText.setText(game.getMoneyPerSecAsString());
        moneyPerTapText.setText(game.getMoneyPerTapAsString());
    }

    public static GUIManager getInstance() {
        return instance;
    }

    public void openFragment(FragmentManager manager, String className, int containerId, Fragment newFragment) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(className);
        ft.replace(containerId, newFragment);
        ft.commit();
    }

    public void showNoUpgradeToast() {
        if (noAvailableUpgradesToast != null) {
            noAvailableUpgradesToast.cancel();
        }
        noAvailableUpgradesToast =
                Toast.makeText(
                        context,
                        R.string.no_upgrades_available,
                        Toast.LENGTH_SHORT
                );
        noAvailableUpgradesToast.show();
    }
}