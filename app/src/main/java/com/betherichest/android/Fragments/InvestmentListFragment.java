package com.betherichest.android.Fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.ListenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.Mangers.Game;
import com.betherichest.android.R;

import java.util.List;

public class InvestmentListFragment extends Fragment {
    View rootView;
    ListView listView;
    Toast noMoneyToast = null;
    Parcelable state;

    Game game;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.investment_list, container, false);
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
                } else {
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

    @Override
    public void onPause() {
        state = listView.onSaveInstanceState();
        super.onPause();
    }
}
