package com.betherichest.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.betherichest.android.GameElements.Upgrade;

import java.util.List;

/**
 * Created by Szabi on 2018.03.27..
 */

public class UpgradeListFragment extends Fragment {
    View rootView;
    Game game;

    List<Upgrade> items;
    Toast noMoneyToast = null;

    public static InvestmentListFragment newInstance() {

        Bundle args = new Bundle();

        InvestmentListFragment fragment = new InvestmentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.upgrade_listview, container, false);
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

        items = game.getDisplayableUpgrades();

        final UpgradeAdapter adapter = new UpgradeAdapter(items, getContext());
        final GridView listView = rootView.findViewById(R.id.upgrade_listview);
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

                Upgrade selectedUpgrade = adapter.getItem(position);
                if (selectedUpgrade.isBuyable()) {
                    game.buyUpgrade(selectedUpgrade);
                    adapter.setItems(game.getDisplayableUpgrades());    //refreshing adapter when an upgrade is bought
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
}
