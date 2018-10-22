package com.betherichest.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.betherichest.android.R;

public class AchievementsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setAchievementsTexts();
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

    private void setAchievementsTexts() {
//        BoostersAdapter adapter = new BoostersAdapter(Game.getInstance().getBoosters());
//        GridView listView = findViewById(R.id.timeWarpGridview);
//        listView.setAdapter(adapter);
    }
}
