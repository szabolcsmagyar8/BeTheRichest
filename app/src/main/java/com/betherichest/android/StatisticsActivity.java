package com.betherichest.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.betherichest.android.Fragments.StatisticsAdapter;

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

    private void setStatTexts() {
        Game.getGameStatistics().createStatItems();
        StatisticsAdapter adapter = new StatisticsAdapter((Game.getGameStatistics().getStatsItems()));
        ListView listView = findViewById(R.id.stat_listview);
        listView.setAdapter(adapter);
    }
}
