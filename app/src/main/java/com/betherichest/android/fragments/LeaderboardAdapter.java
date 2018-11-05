package com.betherichest.android.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betherichest.android.R;
import com.betherichest.android.gameElements.Leader;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

class LeaderboardAdapter extends BaseAdapter {
    private View listItemView;
    private List<Leader> items;

    private TextView nameTextView;
    private TextView moneyTextView;

    private Leader leader;

    public LeaderboardAdapter(List<Leader> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        listItemView = view;

        if (listItemView == null) {
            listItemView = View.inflate(parent.getContext(), R.layout.listitem_leaderboard, null);
        } else {
            listItemView = view;
        }

        leader = items.get(position);
        initializeListUIElements();

        //setUIElementValues(investment);

        return listItemView;
    }

    private void initializeListUIElements() {
        nameTextView = listItemView.findViewById(R.id.leader_name_text);
        moneyTextView = listItemView.findViewById(R.id.leader_money_text);

        nameTextView.setText(leader.getName());
        moneyTextView.setText(String.valueOf(NumberFormat.getInstance(Locale.FRANCE).format((int)leader.getMoney())));
    }
}
