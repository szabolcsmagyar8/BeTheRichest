package com.betherichest.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.gameElements.achievement.Achievement;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.bumptech.glide.Glide;

public class AchievementNotificationActivity extends Activity {
    TextView achievementNameTextView;
    ImageView achievementImageView;
    RelativeLayout notificationLayout;
    private Animation slide_in_from_top, slide_out_to_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_notification);
        initializeAnimation();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Achievement achievement = (Achievement) getIntent().getSerializableExtra("achievement");

        initializeUI(achievement);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                notificationLayout.startAnimation(slide_out_to_top);
            }
        }, 4000);
    }

    private void initializeAnimation() {
        notificationLayout = findViewById(R.id.notification);
        slide_in_from_top = AnimationUtils.loadAnimation(App.getContext(), R.anim.slide_in_from_top);
        slide_out_to_top = AnimationUtils.loadAnimation(App.getContext(), R.anim.slide_out_to_top);
        slide_out_to_top.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        notificationLayout.startAnimation(slide_in_from_top);
    }

    private void initializeUI(Achievement achievement) {
        achievementImageView = findViewById(R.id.achievement_image);
        achievementNameTextView = findViewById(R.id.achievement_name);
        achievementNameTextView.setText(achievement.getDescription());
        Glide.with(App.getContext())
                .load(achievement.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(achievementImageView);
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
