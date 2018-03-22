package com.betherichest.android;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Szabi on 2018. 03. 14..
 */


public class InvestmentListFragment extends android.support.v4.app.Fragment {
    View rootView;
    Toast noMoneyToast = null;

    Game game;

    public static InvestmentListFragment newInstance() {
        Bundle args = new Bundle();

        InvestmentListFragment fragment = new InvestmentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.investment_listview, container, false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        game = Game.getInstance();
        List<Investment> items = game.getInvestments();

        final InvestmentAdapter adapter = new InvestmentAdapter(items);
        final ListView listView = rootView.findViewById(R.id.investment_listview);
        listView.setAdapter(adapter);

        game.adapterRefreshListener = new AdapterRefreshListener() {
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
                }
                else {
                    if (noMoneyToast != null) {
                        noMoneyToast.cancel();
                    }
                    noMoneyToast =
                            Toast.makeText(
                                    getContext(),
                                    R.string.not_enough_money,
                                    Toast.LENGTH_SHORT
                            );
                    noMoneyToast.show();
                }

            }
        });
    }
}
