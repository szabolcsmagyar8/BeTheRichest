package com.betherichest.android.Factories;

import android.content.res.Resources;

import com.betherichest.android.App;
import com.betherichest.android.GameStatistics;
import com.betherichest.android.R;
import com.betherichest.android.StatType;

import java.util.HashMap;


public class StatisticsFactory {
    public static HashMap<StatType, GameStatistics> initializeGameStatistics() {
        HashMap<StatType, GameStatistics> gameStatistics = new HashMap<>();
        Resources res = App.getContext().getResources();

        gameStatistics.put(StatType.TOTAL_CLICKS, new GameStatistics(res.getString(R.string.total_clicks), 0, R.drawable.ic_click));
        gameStatistics.put(StatType.TOTAL_MONEY_FROM_CLICKS, new GameStatistics(res.getString(R.string.total_money_from_clicks), 0.0, R.drawable.ic_total_money));
        gameStatistics.put(StatType.TOTAL_MONEY_COLLECTED, new GameStatistics(res.getString(R.string.total_money_collected), 0.0, R.drawable.ic_total_money));
        gameStatistics.put(StatType.TOTAL_MONEY_SPENT, new GameStatistics(res.getString(R.string.total_money_spent), 0, R.drawable.ic_shopping));
        gameStatistics.put(StatType.TOTAL_TIME_SPENT, new GameStatistics(res.getString(R.string.total_time_spent), 0, R.drawable.ic_timer));

        return gameStatistics;
    }
}
