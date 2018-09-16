package com.betherichest.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.betherichest.android.Fragments.UpgradeListFragment;
import com.betherichest.android.ListenerInterfaces.MoneyChangedListener;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class GUIManager {
    private View view;
    private Game game;

    private WindowManager windowManager;
    private FragmentManager fragmentManager;
    private DrawerLayout mDrawerLayout;

    private TextView currentMoneyText;
    private TextView moneyPerSecText;
    private TextView moneyPerTapText;
    private ImageView dollarImage;
    private ImageView smallDollar;
    private RelativeLayout mainRelativeLayout;
    private ActionBar actionBar;
    private NavigationView navigationView;

    private ImageView investmentsImageView;

    private Toast noAvailableUpgradesToast = null;

    static Random rnd = new Random();

    public GUIManager(View view, WindowManager windowManager, ActionBar supportActionBar, FragmentManager fragmentManager) {
        this.view = view;
        this.windowManager = windowManager;
        this.actionBar = supportActionBar;
        this.fragmentManager = fragmentManager;

        game = Game.getInstance();

        initializeViews();
        initializeEventListeners();
        initializeActionBar(supportActionBar);
    }

    private void initializeViews() {
        currentMoneyText = view.findViewById(R.id.currentMoneyText);
        moneyPerSecText = view.findViewById(R.id.moneyPerSecText);
        moneyPerTapText = view.findViewById(R.id.moneyPerTapText);
        dollarImage = view.findViewById(R.id.dollarImage);
        smallDollar = view.findViewById(R.id.smallDollar);
        investmentsImageView = view.findViewById(R.id.investments);
        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initializeEventListeners() {
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

        investmentsImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //  animateIconPress(view);
                }
                return false;
            }
        });

        game.moneyChangedListener = new MoneyChangedListener() {
            @Override
            public void onMoneyChanged() {
                setMainUITexts();
            }
        };

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_stats:
                                mDrawerLayout.closeDrawers();
                                Intent intent = new Intent(MainActivity.getContext(), StatisticsActivity.class);
                                MainActivity.getContext().startActivity(intent);
                        }
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

    public void onDollarClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            game.dollarClick();

            changeCurrentMoneyText();

            animateDollarTap();
        }
    }

    private void animateDollarTap() {
        dollarImage.startAnimation(AnimationUtils.loadAnimation(MainActivity.getContext(), R.anim.shrink));   // shrink animation
        createAndAnimateTapTextOnDollarImage();
    }

    private void createAndAnimateTapTextOnDollarImage() { // creates a TextView which appears above the dollar icon in a random position, and starts animation
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        final TextView tapText = (TextView) View.inflate(MainActivity.getContext(), R.layout.dollar_tap_popup_text, null);
        tapText.setText("+" + String.valueOf(NumberFormat.getNumberInstance(Locale.FRANCE).format((int) game.getMoneyPerTap()) + "$"));

        tapText.measure(0, 0);

        params.setMargins(getNewRandomXPositionOnDollarImage(tapText), getNewRandomYPositionOnDollarImage(), 0, 0);
        tapText.setLayoutParams(params);
        mainRelativeLayout = view.findViewById(R.id.mainRelativeLayout);
        mainRelativeLayout.addView(tapText);

        Animation growAndFade = AnimationUtils.loadAnimation(MainActivity.getContext(), R.anim.grow_and_fade);
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

    private long mLastClickTime = 0;

    public void openFragment(int containerId, Fragment newFragment) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
            return;
        }

        if (newFragment instanceof UpgradeListFragment && game.getDisplayableUpgrades().size() == 0) {
            showNoUpgradeToast();
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);

        ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
        ft.replace(containerId, newFragment);
        ft.commit();

        setDollarMargin(0);
    }

    public void showNoUpgradeToast() {
        if (noAvailableUpgradesToast != null) {
            noAvailableUpgradesToast.cancel();
        }
        noAvailableUpgradesToast =
                Toast.makeText(
                        MainActivity.getContext(),
                        R.string.no_upgrades_available,
                        Toast.LENGTH_SHORT
                );
        noAvailableUpgradesToast.show();
    }

    public void initializeActionBar(ActionBar actionbar) {
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    protected void setDollarMargin(int marginTop) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.moneyPerTapText);
        params.setMargins(0, convertPixelToDp(marginTop), 0, 0);
        dollarImage.setLayoutParams(params);
    }

    private int convertPixelToDp(int marginTop) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginTop, MainActivity.getContext().getResources().getDisplayMetrics());
    }
}