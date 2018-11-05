package com.betherichest.android.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.betherichest.android.R;
import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.fragments.AchievementAdapter;
import com.betherichest.android.gameElements.Achievement;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;

public class AchievementActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

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

    private void setAdapter() {
        final AchievementAdapter adapter = new AchievementAdapter(Game.getInstance().getAchievements());
        GridView listView = findViewById(R.id.achievements_grid);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Achievement achievement = adapter.getItem(i);
                if (!achievement.isUnlocked()){
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AchievementActivity.this);
                builder
                        .setTitle(achievement.getName())
                        .setMessage(achievement.getDescription())
                        .setIcon(android.R.drawable.btn_star_big_on)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
