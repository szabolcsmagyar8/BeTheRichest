package com.betherichest.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.gameElements.upgrade.Upgrade;
import com.betherichest.android.listenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.mangers.Game;
import com.betherichest.android.mangers.SoundManager;

import java.util.List;


public class UpgradeFragment extends Fragment {
    private View rootView;
    private TextView noUpgradeTextView;
    private Game game = Game.getInstance();

    List<Upgrade> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_upgrade, container, false);
        noUpgradeTextView = rootView.findViewById(R.id.no_upgrade_textview);
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

        items = game.getDisplayableUpgrades();

        final UpgradeAdapter adapter = new UpgradeAdapter(items, getContext());
        final GridView listView = rootView.findViewById(R.id.upgrade_listview);
        listView.setAdapter(adapter);

        game.slowAdapterRefreshListener = new AdapterRefreshListener() {
            @Override
            public void refreshAdapter() {
                if (items.size() == 0) {
                    noUpgradeTextView.setText(App.getContext().getResources().getString(R.string.no_upgrades_available));
                    noUpgradeTextView.setVisibility(View.VISIBLE);
                } else {
                    noUpgradeTextView.setText(null);
                    noUpgradeTextView.setVisibility(View.GONE);
                }
                adapter.setItems(game.getDisplayableUpgrades());
                adapter.notifyDataSetChanged();
                items = game.getDisplayableUpgrades();
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
                    SoundManager.playSound(SoundManager.soundBuy);
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
