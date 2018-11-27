package com.betherichest.android.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.activities.LoginActivity;
import com.betherichest.android.gameElements.Leader;
import com.betherichest.android.mangers.Game;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

class LeaderboardAdapter extends BaseAdapter {
    private View listItemView;
    private List<Leader> items;

    private TextView nameTextView;
    private TextView moneyTextView;
    private TextView rankTextView;
    private RelativeLayout listitemContent;

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

        sort(items, new Comparator<Leader>() {
            @Override
            public int compare(Leader o1, Leader o2) {
                return Double.compare(o2.getMoney(), o1.getMoney());
            }
        });

        leader = items.get(position);
        initializeListUIElements(position);

        return listItemView;
    }

    private void initializeListUIElements(int position) {
        nameTextView = listItemView.findViewById(R.id.leader_name_text);
        moneyTextView = listItemView.findViewById(R.id.leader_money_text);
        rankTextView = listItemView.findViewById(R.id.rank_circle);
        listitemContent = listItemView.findViewById(R.id.listitem_content);
        App.NF.setMaximumFractionDigits(0);
        rankTextView.setText(String.format("#%s", String.valueOf(position + 1)));

        // highlights the player in the list
        if (leader.isPlayer()) {
            listitemContent.setBackground(App.getContext().getResources().getDrawable(R.drawable.background_leaderboard_player));
            moneyTextView.setText(String.valueOf(App.NF.format(Game.getInstance().getCurrentMoney())));
            if (LoginActivity.account != null) {
                nameTextView.setText(LoginActivity.account.getDisplayName());
            } else {
                nameTextView.setText(leader.getName());
            }

        } else {
            listitemContent.setBackgroundColor(App.getContext().getResources().getColor(R.color.transparent));
            moneyTextView.setText(String.valueOf(App.NF.format(leader.getMoney())));
            nameTextView.setText(leader.getName());
        }
    }
}
