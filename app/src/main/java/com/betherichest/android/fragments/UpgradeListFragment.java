package com.betherichest.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.betherichest.android.R;
import com.betherichest.android.gameElements.Upgrade;
import com.betherichest.android.listenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;

import java.util.List;


public class UpgradeListFragment extends Fragment {
    View rootView;
    Game game;

    List<Upgrade> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_upgrade, container, false);
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

        game.slowAdapterRefreshListener = new AdapterRefreshListener() {
            @Override
            public void refreshAdapter() {
                adapter.setItems(game.getDisplayableUpgrades());
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
                    GUIManager.showToast(R.string.not_enough_money);
                }
            }
        });
    }
}
