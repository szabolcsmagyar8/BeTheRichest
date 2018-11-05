package com.betherichest.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.betherichest.android.R;
import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.gameElements.Achievement;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;

public class AchievementNotificationActivity extends Activity {
    TextView achievementNameTextView;
    ImageView achievementImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_notification);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Achievement achievement = (Achievement) getIntent().getSerializableExtra("achievement");

        initializeUI(achievement);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 4000);
    }

    private void initializeUI(Achievement achievement) {
        achievementImageView = findViewById(R.id.achievement_image);
        achievementNameTextView = findViewById(R.id.achievement_name);

        achievementImageView.setImageResource(achievement.getImageResource());
        achievementNameTextView.setText(achievement.getDescription());
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
}
