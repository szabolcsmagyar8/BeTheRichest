package com.betherichest.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.betherichest.android.R;
import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.listenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.mangers.Game;
import com.betherichest.android.mangers.SoundManager;

import java.util.List;

public class InvestmentFragment extends Fragment {
    View rootView;
    ListView listView;

    Game game = Game.getInstance();
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_investment, container, false);
        listView = rootView.findViewById(R.id.investment_listview);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Investment> items = game.getInvestments();

        final InvestmentAdapter adapter = new InvestmentAdapter(items);
        listView.setAdapter(adapter);

        game.slowAdapterRefreshListener = new AdapterRefreshListener() {
            @Override
            public void refreshAdapter() {
                adapter.notifyDataSetChanged();
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Investment selectedInvestment = (Investment) adapter.getItem(position);
                if (selectedInvestment.isBuyable()) {
                    game.buyInvestment(selectedInvestment);
                    adapter.notifyDataSetChanged();
                    SoundManager.playSound(SoundManager.soundBuy);
                } else if (selectedInvestment.isLocked()) {
                    return;
                } else {
                    SoundManager.playSound(SoundManager.soundBottle);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        game.slowAdapterRefreshListener = null;
    }
}
