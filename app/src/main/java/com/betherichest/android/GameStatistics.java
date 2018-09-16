package com.betherichest.android;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class GameStatistics {

    private List<StatisticsItem> statsItems;

    public void createStatItems() {
        statsItems = new ArrayList<>();
        Resources res = MainActivity.getContext().getResources();
        statsItems.add(new StatisticsItem(res.getString(R.string.total_clicks), totalClick));
        statsItems.add(new StatisticsItem(res.getString(R.string.total_money_from_clicks), totalMoneyFromClick));
    }
    private int totalClick = 0;
    private int totalMoneyFromClick = 0;

    public List<StatisticsItem> getStatsItems() {
        return statsItems;
    }

    public class StatisticsItem {

        private String name;
        private int value;

        public StatisticsItem(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValueAsString() {
            return String.valueOf(value);
        }
    }

    public void increaseTotalClicks(double moneyPerTap) {
        totalClick++;
        totalMoneyFromClick += moneyPerTap;
    }
}
