package com.betherichest.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Szabi on 2018.03.04..
 */

public class GUIManager {
    private View view;
    private Game game;
    private static GUIManager instance;

    private Context context;
    private WindowManager windowManager;

    private TextView currentMoneyText;
    private TextView moneyPerSecText;
    private TextView moneyPerTapText;
    private ImageView dollarImage;
    private ImageView smallDollar;
    RelativeLayout mainRelativeLayout;
    private ActionBar actionBar;

    private Toast noAvailableUpgradesToast = null;

    static Random rnd = new Random();

    public GUIManager(View view, Context context, WindowManager windowManager, ActionBar supportActionBar) {
        this.context = context;
        this.windowManager = windowManager;
        this.actionBar = supportActionBar;

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

        initializeIconEventListeners();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeIconEventListeners() {
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
                onDollarClick(event);
                return false;
            }
        });
    }

    public void changeCurrentMoneyText() {
        currentMoneyText.setText(game.getCurrentMoneyAsString());
    }

    public void onDollarClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            game.dollarClick();
            changeCurrentMoneyText();

            animateDollarTap();
        }
    }

    private void animateDollarTap() {
        dollarImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shrink));   // shrink animation

        createAndAnimateTapTextOnDollarImage();
    }

    private void createAndAnimateTapTextOnDollarImage() { // creates a TextView which appears above the dollar icon in a random position, and starts animation
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        final TextView tapText = (TextView) View.inflate(context, R.layout.dollar_tap_popup_text, null);
        tapText.setText("+" + String.valueOf(NumberFormat.getNumberInstance(Locale.FRANCE).format((int) game.getMoneyPerTap()) + "$"));

        tapText.measure(0, 0);

        params.setMargins(getNewRandomXPositionOnDollarImage(tapText), getNewRandomYPositionOnDollarImage(), 0, 0);
        tapText.setLayoutParams(params);
        mainRelativeLayout = view.findViewById(R.id.mainRelativeLayout);
        mainRelativeLayout.addView(tapText);

        Animation growAndFade = AnimationUtils.loadAnimation(context, R.anim.grow_and_fade);
        growAndFade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mainRelativeLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mainRelativeLayout.removeView(tapText);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tapText.startAnimation(growAndFade);
    }

    private int getNewRandomYPositionOnDollarImage() {
        int top = dollarImage.getTop();
        int bottom = dollarImage.getBottom();
        return rnd.nextInt(bottom - top) + top - actionBar.getHeight() / 2;
    }

    private int getNewRandomXPositionOnDollarImage(TextView tapText) {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int left = dollarImage.getLeft();
        int right = dollarImage.getRight();

        int marginLeft = rnd.nextInt(right - left) + left;
        if (marginLeft >= size.x - tapText.getMeasuredWidth()) {    // in case the text would stick out
            marginLeft -= tapText.getMeasuredWidth();
        }
        return marginLeft;
    }

    public void setMainUITexts() {
        currentMoneyText.setText(game.getCurrentMoneyAsString());
        moneyPerSecText.setText(game.getMoneyPerSecAsString());
        moneyPerTapText.setText(game.getMoneyPerTapAsString());
    }

    public static GUIManager getInstance() {
        return instance;
    }

    private long mLastClickTime = 0;

    public void openFragment(FragmentManager manager, String className, int containerId, Fragment newFragment) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
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