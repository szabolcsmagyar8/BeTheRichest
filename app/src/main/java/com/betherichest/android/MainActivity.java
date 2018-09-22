package com.betherichest.android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.betherichest.android.Database.DatabaseManager;
import com.betherichest.android.Fragments.GamblingListFragment;
import com.betherichest.android.Fragments.InvestmentListFragment;
import com.betherichest.android.Fragments.UpgradeListFragment;
import com.betherichest.android.Mangers.GUIManager;
import com.betherichest.android.Mangers.Game;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private GUIManager guiManager;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private DatabaseManager dbManager;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        dbManager = new DatabaseManager();
        dbManager.loadStateFromDb();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        guiManager = new GUIManager(this.findViewById(android.R.id.content), getWindowManager(), getSupportActionBar(), fragmentManager);
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
        guiManager.setDollarMargin(50);
    }
}
