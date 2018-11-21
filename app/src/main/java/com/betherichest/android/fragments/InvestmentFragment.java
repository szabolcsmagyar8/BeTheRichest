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
import com.betherichest.android.mangers.Game;
import com.betherichest.android.mangers.SoundManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InvestmentFragment extends Fragment {
    private View rootView;
    private ListView listView;

    private Game game = Game.getInstance();
    private InvestmentAdapter adapter;

    private int index = -1;
    private Runnable investmentPriceWatch = new Runnable() {
        @Override
        public void run() {
            double act = game.getCurrentMoney();
            List<Double> investmentPrices = new ArrayList<>();
            for (Investment investment : game.getInvestments()) {
                investmentPrices.add(investment.getPrice());
            }
            investmentPrices.add(act);
            Collections.sort(investmentPrices, new Comparator<Double>() {
                @Override
                public int compare(Double d1, Double d2) {
                    return Double.compare(d1, d2);

                }
            });
            setIndex(investmentPrices.lastIndexOf(act));

            game.handler.postDelayed(investmentPriceWatch, 100);
        }
    };

    public void setIndex(int index) {
        if (this.index != index) {
            this.index = index;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

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

        adapter = new InvestmentAdapter(items);
        listView.setAdapter(adapter);

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

        game.handler.post(investmentPriceWatch);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        game.handler.removeCallbacks(investmentPriceWatch);
    }
}
