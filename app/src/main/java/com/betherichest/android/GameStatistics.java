package com.betherichest.android;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class GameStatistics {

    private double totalClick = 0;
    private double totalMoneyFromClick = 0;
    private double totalMoneyCollected = 0;
    private List<StatisticsItem> statsItems;

    public void createStatItems() {
        statsItems = new ArrayList<>();
        Resources res = MainActivity.getContext().getResources();
        statsItems.add(new StatisticsItem(res.getString(R.string.total_money_collected), totalMoneyCollected, R.drawable.ic_total_money));
        statsItems.add(new StatisticsItem(res.getString(R.string.total_clicks), Math.round(totalClick), R.drawable.ic_click));
        statsItems.add(new StatisticsItem(res.getString(R.string.total_money_from_clicks), Math.round(totalMoneyFromClick), R.drawable.ic_money));
    }

    public List<StatisticsItem> getStatsItems() {
        return statsItems;
    }

    public void increaseTotalClicks(double moneyPerTap) {
        totalClick++;
        totalMoneyFromClick += moneyPerTap;
        totalMoneyCollected += moneyPerTap;
    }

    public void addMoney(double money) {
        totalMoneyCollected += money;
    }

    public class StatisticsItem {

        private String name;
        private double value;
        private int imageResource;

        public StatisticsItem(String name, double value, int imageResource) {
            this.name = name;
            this.value = value;
            this.imageResource = imageResource;
        }

        public String getName() {
            return name;
        }

        public String getValueAsString() {
            return String.valueOf(Math.round(value * 100.0) / 100.0);
        }

        public int getImageResource() {
            return imageResource;
        }
    }
}
