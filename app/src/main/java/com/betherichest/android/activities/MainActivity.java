package com.betherichest.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.fragments.GamblingListFragment;
import com.betherichest.android.fragments.InvestmentListFragment;
import com.betherichest.android.fragments.UpgradeListFragment;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.betherichest.android.mangers.StatisticsManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private GUIManager guiManager;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private DatabaseManager dbManager;
    private DrawerLayout mDrawerLayout;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                guiManager.changeAdRewardMenuItemText();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                guiManager.changeAdRewardMenuItemText();
            }
        });

        dbManager = new DatabaseManager();
        dbManager.loadStateFromDb();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        guiManager = new GUIManager(this.findViewById(android.R.id.content), getWindowManager(), getSupportActionBar(), fragmentManager);
        initNavigatonViewListener();
        initAds();
    }

    private void initAds() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }

        });
    }

    public static Context getContext() {
        return context;
    }

    public void investmentsIconClick(View view) {
        guiManager.openFragment(R.id.investment_list_container, new InvestmentListFragment());
    }

    public void upgradesIconClick(View view) {
        guiManager.openFragment(R.id.upgrade_list_container, new UpgradeListFragment());
    }

    public void gamblingIconClick(View view) {
        guiManager.openFragment(R.id.gambling_list_container, new GamblingListFragment());
    }

    public void leaderboardIconClick(View view) {
        return;
    }

    public void closeClick(View view) {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Game.setTimerPaused(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!GUIManager.isActivityOpened()) {
            Game.setTimerPaused(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!GUIManager.isActivityOpened()) {
            Game.setTimerPaused(true);
        }
        dbManager.saveStateToDb();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (Game.isGamblingAnimationRunning()) {
            return;
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
        guiManager.relocateDollarImage(false);
    }

    private void initNavigatonViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_view);
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
                                GUIManager.setActivityOpened(true);
                                break;
                            case R.id.nav_boosters:
                                mDrawerLayout.closeDrawers();
                                intent = new Intent(context, BoostersActivity.class);
                                context.startActivity(intent);
                                GUIManager.setActivityOpened(true);
                                break;
                            case R.id.nav_achievements:
                                mDrawerLayout.closeDrawers();
                                intent = new Intent(context, AchievementsActivity.class);
                                context.startActivity(intent);
                                GUIManager.setActivityOpened(true);
                                break;
                            case R.id.nav_ads:
                                if (App.isOnline()) {
                                    mDrawerLayout.closeDrawers();
                                    intent = new Intent(context, AdWatcherActivity.class);
                                    startActivityForResult(intent, 1);
                                    GUIManager.setActivityOpened(true);
                                } else {
                                    GUIManager.showToast(R.string.check_net_connection);
                                }
                                break;
                            case R.id.nav_settings:
                                mDrawerLayout.closeDrawers();
                                intent = new Intent(context, SettingsActivity.class);
                                context.startActivity(intent);
                                GUIManager.setActivityOpened(true);
                                break;
                            case R.id.nav_about:
                                mDrawerLayout.closeDrawers();
                                intent = new Intent(context, AboutActivity.class);
                                context.startActivity(intent);
                                GUIManager.setActivityOpened(true);
                                break;
                            case R.id.nav_profile:
                                mDrawerLayout.closeDrawers();
                                intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                                GUIManager.setActivityOpened(true);
                                break;
                            case R.id.nav_help:
                                mDrawerLayout.closeDrawers();
                                intent = new Intent(context, SettingsActivity.class);
                                context.startActivity(intent);
                                GUIManager.setActivityOpened(true);
                                break;
                        }
                        return false;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                showRewardDialog(Game.getInstance().getAdReward());
            }
        }
    }

    private void showRewardDialog(final double rewardMoney) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Video reward")
                .setMessage("You received " + String.valueOf(NumberFormat.getNumberInstance(Locale.FRANCE).format(rewardMoney)) + "$")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Game.getInstance().earnMoney(rewardMoney);
                    }
                })
                .show();
    }
}
