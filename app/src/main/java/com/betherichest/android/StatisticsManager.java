package com.betherichest.android;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.betherichest.android.Factories.StatisticsFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class StatisticsManager {

    private HashMap<StatType, GameStatistics> gameStatistics;

    public StatisticsManager() {
        gameStatistics = StatisticsFactory.initializeGameStatistics();
    }

    public List<GameStatistics> getGameStatistics() {
        List<GameStatistics> statList = new ArrayList<>(gameStatistics.values());
        Collections.sort(statList, new Comparator<GameStatistics>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(GameStatistics o1, GameStatistics o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        return statList;
    }

    public void dollarClick(double moneyPerTap) {
        gameStatistics.get(StatType.TOTAL_CLICKS).increaseValueByOne();
        gameStatistics.get(StatType.TOTAL_MONEY_FROM_CLICKS).increaseValueByAmount(moneyPerTap);
        gameStatistics.get(StatType.TOTAL_MONEY_COLLECTED).increaseValueByAmount(moneyPerTap);
    }

    public void earnMoney(double moneyPerSec) {
        gameStatistics.get(StatType.TOTAL_MONEY_COLLECTED).increaseValueByAmount(moneyPerSec);
    }

    public void buyItem(double price) {
        gameStatistics.get(StatType.TOTAL_MONEY_SPENT).increaseValueByAmount(price);
    }
}
