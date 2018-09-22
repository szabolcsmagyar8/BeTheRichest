package com.betherichest.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.betherichest.android.Fragments.StatisticsAdapter;
import com.betherichest.android.Mangers.GUIManager;
import com.betherichest.android.Mangers.Game;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setStatTexts();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Game.setTimerPaused(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                GUIManager.setActivityOpened(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setStatTexts() {
        StatisticsAdapter adapter = new StatisticsAdapter(Game.statisticsManager.getGameStatistics());
        ListView listView = findViewById(R.id.stat_listview);
        listView.setAdapter(adapter);
    }
}
