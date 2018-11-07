package com.betherichest.android.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.gameElements.GameStatistics;
import com.betherichest.android.listenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.mangers.Game;

import java.util.List;

public class StatisticsAdapter extends BaseAdapter {
    private View listItemView;
    private TextView statTextView;
    private TextView statValueTextView;
    private ImageView iconImageView;
    private List<GameStatistics> items;

    public StatisticsAdapter(List<GameStatistics> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
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
            listItemView = View.inflate(App.getContext(), R.layout.listitem_statistics, null);
        } else {
            listItemView = view;
        }

        Game.getInstance().smoothAdapterRefreshListener = new AdapterRefreshListener() {
            @Override
            public void refreshAdapter() {
                StatisticsAdapter.super.notifyDataSetChanged();
            }
        };

        statTextView = listItemView.findViewById(R.id.statText);
        statValueTextView = listItemView.findViewById(R.id.statValueText);
        iconImageView = listItemView.findViewById(R.id.iconImage);

        statTextView.setText(items.get(i).getName());
        statValueTextView.setText(items.get(i).getValueAsString());
        iconImageView.setImageResource(items.get(i).getImageResource());

        return listItemView;
    }
}
