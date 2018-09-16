package com.betherichest.android.Fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betherichest.android.GameStatistics;
import com.betherichest.android.MainActivity;
import com.betherichest.android.R;

import java.util.List;

public class StatisticsAdapter extends BaseAdapter {
    private View listItemView;
    private TextView statTextView;
    private TextView statValueTextView;
    private List<GameStatistics.StatisticsItem> items;

    public StatisticsAdapter(List<GameStatistics.StatisticsItem> items) {
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
            listItemView = View.inflate(MainActivity.getContext(), R.layout.statistics_listitem, null);
        } else {
            listItemView = view;
        }

        statTextView = listItemView.findViewById(R.id.statText);
        statValueTextView = listItemView.findViewById(R.id.statValueText);
        statTextView.setText(items.get(i).getName());
        statValueTextView.setText(items.get(i).getValueAsString());

        return listItemView;
    }
}
