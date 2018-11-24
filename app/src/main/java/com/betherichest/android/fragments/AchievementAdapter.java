package com.betherichest.android.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.gameElements.achievement.Achievement;

import java.util.List;

public class AchievementAdapter extends BaseAdapter {
    private View listItemView;
    private ImageView iconImageView;
    private TextView textView;
    private List<Achievement> items;
    private RelativeLayout achievementContainer;
    private RelativeLayout labelBackgroundLayout;

    public AchievementAdapter(List<Achievement> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Achievement getItem(int i) {
        return items == null ? null : items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        listItemView = view;
        if (listItemView == null) {
            listItemView = View.inflate(App.getContext(), R.layout.listitem_achievement, null);
        } else {
            listItemView = view;
        }

        Achievement achievement = items.get(i);

        iconImageView = listItemView.findViewById(R.id.achievement_icon);
        textView = listItemView.findViewById(R.id.achievement_text);
        labelBackgroundLayout = listItemView.findViewById(R.id.label_background);
        achievementContainer = listItemView.findViewById(R.id.achievement_container);

        if (items.get(i).isUnlocked()) {
            iconImageView.setImageResource(achievement.getImageResource());
            achievementContainer.setBackgroundColor(App.getContext().getResources().getColor(R.color.achievement_active));
            labelBackgroundLayout.setVisibility(View.VISIBLE);
            textView.setText(achievement.getText());
        } else {
            iconImageView.setImageResource(R.drawable.questionmark);
            achievementContainer.setBackgroundColor(App.getContext().getResources().getColor(R.color.achievement_inactive));
            labelBackgroundLayout.setVisibility(View.GONE);
            textView.setText("");
        }

        return listItemView;
    }
}
