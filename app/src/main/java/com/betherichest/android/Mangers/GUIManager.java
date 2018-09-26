package com.betherichest.android.Mangers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
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

import com.betherichest.android.Activities.BoostersActivity;
import com.betherichest.android.Activities.StatisticsActivity;
import com.betherichest.android.Fragments.UpgradeListFragment;
import com.betherichest.android.ListenerInterfaces.MoneyChangedListener;
import com.betherichest.android.R;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class GUIManager {
    private View view;
    private Game game;
    private Context context;

    private WindowManager windowManager;
    private FragmentManager fragmentManager;

    private TextView currentMoneyText;
    private TextView moneyPerSecText;
    private TextView moneyPerTapText;
    private ImageView dollarImage;
    private ImageView smallDollar;
    private static boolean activityOpened = false;
    private ImageView investmentsImageView;
    private ImageView upgradesImageView;
    private ImageView gamblingImageView;
    private RelativeLayout mainRelativeLayout;
    private ImageView leaderboardImageView;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private Toast noAvailableUpgradesToast = null;

    static Random rnd = new Random();
    private DrawerLayout mDrawerLayout;

    public GUIManager(View view, WindowManager windowManager, ActionBar supportActionBar, FragmentManager fragmentManager) {
        this.view = view;
        this.windowManager = windowManager;
        this.actionBar = supportActionBar;
        this.fragmentManager = fragmentManager;
        context = view.getContext();

        game = Game.getInstance();

        initializeViews();
        initializeEventListeners();
        initializeActionBar(supportActionBar);
        setMainUITexts();
    }

    public static boolean isActivityOpened() {
        return activityOpened;
    }

    public static void setActivityOpened(boolean opened) {
        activityOpened = opened;
    }

    private void initializeViews() {
        currentMoneyText = view.findViewById(R.id.currentMoneyText);
        moneyPerSecText = view.findViewById(R.id.moneyPerSecText);
        moneyPerTapText = view.findViewById(R.id.moneyPerTapText);
        dollarImage = view.findViewById(R.id.dollarImage);
        smallDollar = view.findViewById(R.id.smallDollar);
        investmentsImageView = view.findViewById(R.id.investmentsIcon);
        upgradesImageView = view.findViewById(R.id.upgradesIcon);
        gamblingImageView = view.findViewById(R.id.gamblingIcon);
        leaderboardImageView = view.findViewById(R.id.leaderboardIcon);

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
                highlightIconOnPress(view, motionEvent);
                return false;
            }
        });

        upgradesImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                highlightIconOnPress(view, motionEvent);
                return false;
            }
        });

        gamblingImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                highlightIconOnPress(view, motionEvent);
                return false;
            }
        });

        leaderboardImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                highlightIconOnPress(view, motionEvent);
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
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_stats:
                                mDrawerLayout.closeDrawers();
                                intent = new Intent(context, StatisticsActivity.class);
                                StatisticsManager.getInstance().initailizeBasicStats();
                                context.startActivity(intent);
                                break;
                            case R.id.nav_boosters:
                                mDrawerLayout.closeDrawers();
                                intent = new Intent(context, BoostersActivity.class);
                                context.startActivity(intent);
                                break;
                        }
                        activityOpened = true;
                        return false;
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

    private void highlightIconOnPress(View view, MotionEvent motionEvent) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(25);
        shape.setColor(context.getResources().getColor(R.color.iconHighlightColor));
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ((RelativeLayout) view.getParent()).setBackground(shape);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ((RelativeLayout) view.getParent()).setBackgroundColor(Color.TRANSPARENT);
                break;
        }
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

        relocateDollarImage(true);
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

    public void initializeActionBar(ActionBar actionbar) {
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    public void relocateDollarImage(boolean toUp) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (toUp) {
            params.addRule(RelativeLayout.BELOW, R.id.moneyPerTapText);
            dollarImage.setPadding(0, 0, 0, 0);
        } else {
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            dollarImage.setPadding(0, 0, 0, 50);
        }
        dollarImage.setLayoutParams(params);
    }

    private int convertPixelToDp(int marginTop) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginTop, context.getResources().getDisplayMetrics());
    }
}