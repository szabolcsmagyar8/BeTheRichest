package com.betherichest.android.mangers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
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

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.fragments.UpgradeFragment;
import com.betherichest.android.listenerInterfaces.MoneyChangedListener;

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
    private ImageView investmentsImageView;
    private ImageView upgradesImageView;
    private ImageView gamblingImageView;
    private RelativeLayout mainRelativeLayout;
    private ImageView leaderboardImageView;
    private ActionBar actionBar;

    MediaPlayer mp;

    private static Toast toast = null;
    private static boolean activityOpened = false;

    static Random rnd = new Random();
    private long mLastClickTime = 0;

    public GUIManager(View view, WindowManager windowManager, ActionBar supportActionBar, FragmentManager fragmentManager) {
        this.view = view;
        this.windowManager = windowManager;
        this.actionBar = supportActionBar;
        this.fragmentManager = fragmentManager;
        context = App.getContext();

        game = Game.getInstance();

        initializeViews();
        initializeEventListeners();
        initializeActionBar(supportActionBar);
        setMainUIItems();
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
        mainRelativeLayout = view.findViewById(R.id.mainRelativeLayout);
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
                setMainUIItems();
            }
        };
    }

    public void changeCurrentMoneyText() {
        currentMoneyText.setText(game.getCurrentMoneyAsString());
    }

    public void setMainUIItems() {
        currentMoneyText.setText(game.getCurrentMoneyAsString());
        moneyPerSecText.setText(game.getMoneyPerSecAsString());
        moneyPerTapText.setText(game.getMoneyPerTapAsString());
    }

    public void onDollarClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            game.dollarClick();

            changeCurrentMoneyText();

            animateDollarTap();

           // playSound();
        }
    }

//    private void playSound() {
//        mp = MediaPlayer.create(context, R.raw.click);
//
//        try {
//            if (mp.isPlaying()) {
//                mp.stop();
//                mp.release();
//                mp = MediaPlayer.create(context, R.raw.click);
//            } mp.start();
//        } catch(Exception e) { e.printStackTrace(); }
//    }

    private void animateDollarTap() {
        dollarImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shrink));   // shrink animation
        createAndAnimateTapTextOnDollarImage();
    }

    private void createAndAnimateTapTextOnDollarImage() { // creates a TextView which appears above the dollar icon in a random position, and starts animation
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        final TextView tapText = (TextView) View.inflate(context, R.layout.text_dollar_tap_popup, null);
        tapText.setText("+" + String.valueOf(App.NF.format((int) game.getMoneyPerTap()) + "$"));

        tapText.measure(0, 0);

        params.setMargins(getNewRandomXPositionOnDollarImage(tapText), getNewRandomYPositionOnDollarImage(), 0, 0);
        tapText.setLayoutParams(params);
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
        int yPos = rnd.nextInt(bottom - dollarImage.getPaddingBottom() - top) + top - actionBar.getHeight() / 2;
        return yPos;
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

    public void openFragment(int containerId, Fragment fragment, Bundle bundle) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
            return;
        }

        if (fragment instanceof UpgradeFragment && game.getDisplayableUpgrades().size() == 0) {
            showToast(R.string.no_upgrades_available);
            return;
        }

        if (fragmentManager == null) {
            return;
        }

        fragment.setArguments(bundle);

        mLastClickTime = SystemClock.elapsedRealtime();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom, R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
        ft.addToBackStack(null);

        ft.replace(containerId, fragment).commitAllowingStateLoss();
    }

    public static void showToast(int stringResource) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(App.getContext(), stringResource, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToast(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
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
            dollarImage.setPadding(0, 0, 0, convertPixelToDp(50));
        }
        dollarImage.setLayoutParams(params);
    }

    private int convertPixelToDp(int pixel) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, context.getResources().getDisplayMetrics());
    }

    public void changeAdRewardMenuItemText() {
        NavigationView navigationView = view.findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem navAds = menu.findItem(R.id.nav_ads);
        String rewardString = String.valueOf(App.NF.format(game.getAdReward()));
        navAds.setTitle(App.getContext().getResources().getString(R.string.ads) + " " + rewardString + " $");
    }

}