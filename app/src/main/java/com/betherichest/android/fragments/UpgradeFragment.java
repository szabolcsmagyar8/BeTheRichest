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
import com.betherichest.android.mangers.Game;
import com.betherichest.android.mangers.SoundManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UpgradeFragment extends Fragment {
    private View rootView;
    private TextView noUpgradeTextView;
    private Game game = Game.getInstance();
    private GridView gridView;

    private List<Upgrade> items;

    private UpgradeAdapter adapter;

    private int index = -1;
    private Runnable upgradePriceWatch = new Runnable() {
        @Override
        public void run() {
            double act = game.getCurrentMoney();
            List<Double> upgradePrices = new ArrayList<>();
            for (Upgrade upgrade : game.getDisplayableUpgrades()) {
                upgradePrices.add(upgrade.getPrice());
            }
            upgradePrices.add(act);
            Collections.sort(upgradePrices, new Comparator<Double>() {
                @Override
                public int compare(Double d1, Double d2) {
                    return Double.compare(d1, d2);

                }
            });
            setIndex(upgradePrices.lastIndexOf(act));

            game.handler.postDelayed(upgradePriceWatch, 100);
        }
    };

    public void setIndex(int index) {
        if (this.index != index) {
            this.index = index;
            if (adapter != null) {
                if (items.size() == 0) {
                    noUpgradeTextView.setText(App.getContext().getResources().getString(R.string.no_upgrades_available));
                } else if (noUpgradeTextView.getText() != null) {
                    noUpgradeTextView.setText(null);
                }
                items = game.getDisplayableUpgrades();
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_upgrade, container, false);
        noUpgradeTextView = rootView.findViewById(R.id.no_upgrade_textview);
        gridView = rootView.findViewById(R.id.upgrade_listview);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        items = game.getDisplayableUpgrades();

        adapter = new UpgradeAdapter(items);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Upgrade selectedUpgrade = adapter.getItem(position);
                if (selectedUpgrade.isBuyable()) {
                    game.buyUpgrade(selectedUpgrade);
//                    items = game.getDisplayableUpgrades();
                    items.remove(selectedUpgrade);
                    adapter.setItems(items);    //refreshing adapter when an upgrade is bought
                    adapter.notifyDataSetChanged();
                    SoundManager.playSound(SoundManager.soundBuy);
                } else {
                    SoundManager.playSound(SoundManager.soundBottle);
                }
            }
        });

        game.handler.post(upgradePriceWatch);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        game.handler.removeCallbacks(upgradePriceWatch);
    }
}
