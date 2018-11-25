package com.betherichest.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.betherichest.android.R;
import com.betherichest.android.gameElements.Leader;
import com.betherichest.android.mangers.Game;

import java.util.List;

public class LeaderboardFragment extends Fragment {
    private static LeaderboardAdapter adapter;
    private View rootView;
    private ListView listView;
    private Game game = Game.getInstance();

    public static void update() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        listView = rootView.findViewById(R.id.leaderboard_listview);

        return rootView;
    }

    private boolean playerWasAdded(List<Leader> leaders) {
        for (Leader leader : leaders) {
            if (leader.isPlayer()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Leader> items = game.getLeaders();

        if (!playerWasAdded(items)) {
            items.add(new Leader("Player", game.getCurrentMoney(), true));
        }

        adapter = new LeaderboardAdapter(items);

        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
    }
}
