package com.betherichest.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.betherichest.android.R;
import com.betherichest.android.fragments.StatisticsAdapter;
import com.betherichest.android.listenerInterfaces.RefreshListener;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;

public class StatisticsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Game.setTimerPaused(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Game.setTimerPaused(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        GUIManager.setActivityOpened(false);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter() {
        final StatisticsAdapter adapter = new StatisticsAdapter(Game.statisticsManager.getGameStatistics());
        Game.getInstance().smoothRefreshListener = new RefreshListener() {
            @Override
            public void refresh() {
                adapter.notifyDataSetChanged();
            }
        };

        final ListView listView = findViewById(R.id.stat_listview);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Game.getInstance().smoothRefreshListener = null;
    }
}
