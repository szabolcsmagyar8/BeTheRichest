package com.betherichest.android.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.betherichest.android.R;
import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.listenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;

import java.util.List;

public class InvestmentListFragment extends Fragment {
    View rootView;
    ListView listView;
    Parcelable state;

    Game game;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_investment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        game = Game.getInstance();
        List<Investment> items = game.getInvestments();

        final InvestmentAdapter adapter = new InvestmentAdapter(items);
        listView = rootView.findViewById(R.id.investment_listview);

        listView.setAdapter(adapter);

        if (state != null) {
            listView.onRestoreInstanceState(state);
        }

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
                } else if (selectedInvestment.isLocked()) {
                    return;
                } else {
                    GUIManager.showToast(R.string.not_enough_money);
                }
            }
        });
    }

    @Override
    public void onPause() {
        state = listView.onSaveInstanceState();
        super.onPause();
    }
}
