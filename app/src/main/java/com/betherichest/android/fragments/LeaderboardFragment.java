package com.betherichest.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.betherichest.android.R;
import com.betherichest.android.factories.LeaderFactory;
import com.betherichest.android.gameElements.Leader;
import com.betherichest.android.listenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.mangers.Game;

import java.util.List;

public class LeaderboardFragment extends Fragment {
    View rootView;
    ListView listView;

    Game game = Game.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Leader> items = LeaderFactory.getLeaders();

        final LeaderboardAdapter adapter = new LeaderboardAdapter(items);
        listView = rootView.findViewById(R.id.leaderboard_listview);

        listView.setAdapter(adapter);

        game.slowAdapterRefreshListener = new AdapterRefreshListener() {
            @Override
            public void refreshAdapter() {
                adapter.notifyDataSetChanged();
            }
        };
    }
}
