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
import com.betherichest.android.HTTPMethod;
import com.betherichest.android.R;
import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.fragments.GamblingFragment;
import com.betherichest.android.fragments.InvestmentFragment;
import com.betherichest.android.fragments.LeaderboardFragment;
import com.betherichest.android.fragments.UpgradeFragment;
import com.betherichest.android.mangers.ConnectionManager;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
        initAdBanner();
    }

    private void initAdBanner() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void investmentsIconClick(View view) {
        guiManager.openFragment(R.id.investment_list_container, new InvestmentFragment());
    }

    public void upgradesIconClick(View view) {
        guiManager.openFragment(R.id.upgrade_list_container, new UpgradeFragment());
    }

    public void gamblingIconClick(View view) {
        guiManager.openFragment(R.id.gambling_list_container, new GamblingFragment());
    }

    public void leaderboardIconClick(View view) {
        guiManager.openFragment(R.id.leaderboard_list_container, new LeaderboardFragment());
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

        try {
            //new ConnectionManager(new URL("http://httpbin.org/post"), Game.statisticsManager.getStatRequestParams());
            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiNWJlNWJjOTQ4MDI4OGMwMDExMjZlNDUzIiwiaWF0IjoxNTQxNzgyNjc2LCJleHAiOjEwMTgxNzgyNjc2fQ.EnGfQLYHsgr63fD-FJ8ujH9aJ0uONw1sTZwbYCN49vw");
            new ConnectionManager(new URL(ConnectionManager.BTR_URL + "/muser/log-stats"), Game.statisticsManager.getStatRequestParams(), header, HTTPMethod.POST);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
            guiManager.relocateDollarImage(false);
        } else {
            super.onBackPressed();
        }
    }

    private void initNavigatonViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_stats:
                                openActivity(new Intent(context, StatisticsActivity.class));
                                break;
                            case R.id.nav_boosters:
                                openActivity(new Intent(context, BoosterActivity.class));
                                break;
                            case R.id.nav_achievements:
                                openActivity(new Intent(context, AchievementActivity.class));
                                break;
                            case R.id.nav_ads:
                                if (App.isOnline()) {
                                    mDrawerLayout.closeDrawers();
                                    Intent intent = new Intent(context, AdWatcherActivity.class);
                                    startActivityForResult(intent, 1);
                                    GUIManager.setActivityOpened(true);
                                } else {
                                    GUIManager.showToast(R.string.check_net_connection);
                                }
                                break;
                            case R.id.nav_settings:
                                openActivity(new Intent(context, SettingsActivity.class));
                                break;
                            case R.id.nav_about:
                                openActivity(new Intent(context, AboutActivity.class));
                                break;
                            case R.id.nav_profile:
                                openActivity(new Intent(context, LoginActivity.class));
                                break;
                            case R.id.nav_help:
                                openActivity(new Intent(context, SettingsActivity.class));
                                break;
                        }
                        return false;
                    }
                });
    }

    private void openActivity(Intent intent) {
        mDrawerLayout.closeDrawers();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        GUIManager.setActivityOpened(true);
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

    public void removeAds(View view) {
        GUIManager.showToast(R.string.remove_ads);
    }
}
