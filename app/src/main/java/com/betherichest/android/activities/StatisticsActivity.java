package com.betherichest.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.fragments.StatisticsAdapter;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.betherichest.android.R;

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
    protected void onResume() {
        super.onResume();
        Game.setTimerPaused(false);
    }

    @Override
    protected void onPause() {
        Game.setTimerPaused(true);
        super.onPause();
    }

    @Override
    protected void onStop() {
        DatabaseManager.instance.saveStateToDb();
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
