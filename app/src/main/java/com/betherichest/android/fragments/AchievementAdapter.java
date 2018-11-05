package com.betherichest.android.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.gameElements.achievement.Achievement;

import java.util.List;

public class AchievementAdapter extends BaseAdapter {
    private View listItemView;
    private ImageView iconImageView;
    private List<Achievement> items;

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

        iconImageView = listItemView.findViewById(R.id.achievement_icon);
        RelativeLayout itemLayout = listItemView.findViewById(R.id.achievement_item);

        if (items.get(i).isUnlocked()) {
            iconImageView.setImageResource(items.get(i).getImageResource());
            iconImageView.setBackgroundColor(App.getContext().getResources().getColor(R.color.achievement_active));
        } else {
            iconImageView.setImageResource(R.drawable.questionmark);
            iconImageView.setBackgroundColor(App.getContext().getResources().getColor(R.color.achievement_inactive));
            itemLayout.setBackground(null);
        }

        return listItemView;
    }
}
