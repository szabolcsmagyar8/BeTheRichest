package com.betherichest.android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betherichest.android.R;
import com.betherichest.android.gameElements.Achievement;

public class AchievementNotificationFragment extends Fragment {
    View rootView;
    TextView achievementNameTextView;
    ImageView achievementIconImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_achievement_notification, container, false);
        savedInstanceState = getArguments();

        Achievement achievement = (Achievement) savedInstanceState.getSerializable("achievement");

        achievementNameTextView = rootView.findViewById(R.id.achievement_name);
        achievementIconImageView = rootView.findViewById(R.id.achievement_image);

        achievementNameTextView.setText(achievement.getDescription());
        achievementIconImageView.setImageResource(achievement.getImageResource());

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}
