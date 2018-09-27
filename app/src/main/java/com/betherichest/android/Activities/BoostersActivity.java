package com.betherichest.android.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;

import com.betherichest.android.Database.DatabaseManager;
import com.betherichest.android.Fragments.BoostersAdapter;
import com.betherichest.android.Mangers.GUIManager;
import com.betherichest.android.Mangers.Game;
import com.betherichest.android.R;

public class BoostersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boosters);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setBoosterTexts();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Game.setTimerPaused(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DatabaseManager.instance.saveStateToDb();
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

    private void setBoosterTexts() {
        BoostersAdapter adapter = new BoostersAdapter(Game.getInstance().getBoosters());
        GridView listView = findViewById(R.id.timeWarpGridview);
        listView.setAdapter(adapter);
    }
}