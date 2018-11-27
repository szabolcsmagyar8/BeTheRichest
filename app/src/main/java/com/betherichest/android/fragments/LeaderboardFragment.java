package com.betherichest.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.gameElements.Leader;
import com.betherichest.android.listenerInterfaces.RefreshListener;
import com.betherichest.android.mangers.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardFragment extends Fragment {
    private static LeaderboardAdapter adapter;
    private View rootView;
    private ListView listView;
    private Game game = Game.getInstance();
    private List<Leader> leaders = game.getLeaders();

    private int index = -1;
    private Runnable leaderMoneyWatch = new Runnable() {
        @Override
        public void run() {
            double act = game.getCurrentMoney();
            List<Double> leaderMoneys = new ArrayList<>();
            for (Leader leader : leaders) {
                leaderMoneys.add(leader.getMoney());
            }
            leaderMoneys.add(act);
            Collections.sort(leaderMoneys, new Comparator<Double>() {
                @Override
                public int compare(Double d1, Double d2) {
                    return Double.compare(d1, d2);
                }
            });
            setIndex(leaderMoneys.lastIndexOf(act));

            game.handler.postDelayed(leaderMoneyWatch, 100);
        }
    };

    public void setIndex(int index) {
        if (this.index != index) {
            this.index = index;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        listView = rootView.findViewById(R.id.leaderboard_listview);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new LeaderboardAdapter(leaders);
        listView.setAdapter(adapter);

        Game.getInstance().leaderRefreshListener = new RefreshListener() {
            @Override
            public void refresh() {
                for (int i = listView.getFirstVisiblePosition(); i < listView.getLastVisiblePosition(); i++) {
                    setMoneyTextViewByPosition(i);
                }
            }
        };
        game.handler.post(leaderMoneyWatch);
    }

    private void setMoneyTextViewByPosition(int pos) {
        TextView valueTextView = listView.getChildAt(pos - listView.getFirstVisiblePosition()).findViewById(R.id.leader_money_text);
        App.NF.setMaximumFractionDigits(0);
        valueTextView.setText(String.valueOf(App.NF.format(leaders.get(pos).getMoney())));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
        game.leaderRefreshListener = null;
    }
}
