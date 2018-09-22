package com.betherichest.android.Factories;

import android.content.res.Resources;

import com.betherichest.android.App;
import com.betherichest.android.GameElements.GameStatistics;
import com.betherichest.android.R;
import com.betherichest.android.StatType;

import java.util.Date;
import java.util.HashMap;


public class StatisticsFactory {
    public static HashMap<StatType, GameStatistics> initializeGameStatistics() {
        HashMap<StatType, GameStatistics> gameStatistics = new HashMap<>();
        Resources res = App.getContext().getResources();

        gameStatistics.put(StatType.TOTAL_MONEY_COLLECTED, new GameStatistics(res.getString(R.string.total_money_collected), 0.0, R.drawable.ic_money));
        gameStatistics.put(StatType.TOTAL_CLICKS, new GameStatistics(res.getString(R.string.total_clicks), 0, R.drawable.ic_click));
        gameStatistics.put(StatType.TOTAL_MONEY_FROM_CLICKS, new GameStatistics(res.getString(R.string.total_money_from_clicks), 0.0, R.drawable.ic_money));
        gameStatistics.put(StatType.MONEY_SPENT, new GameStatistics(res.getString(R.string.money_spent), 0, R.drawable.ic_shopping));
        gameStatistics.put(StatType.TOTAL_PLAYING_TIME, new GameStatistics(res.getString(R.string.total_playing_time), 0, R.drawable.ic_timer));
        gameStatistics.put(StatType.TOTAL_GAMBLING, new GameStatistics(res.getString(R.string.total_gambling), 0, R.drawable.ic_clover));
        gameStatistics.put(StatType.GAMBLING_WINS, new GameStatistics(res.getString(R.string.gambling_wins), 0, R.drawable.ic_clover));
        gameStatistics.put(StatType.GAMBLING_LOSES, new GameStatistics(res.getString(R.string.gambling_loses), 0, R.drawable.ic_clover));
        gameStatistics.put(StatType.MONEY_FROM_GAMBLING, new GameStatistics(res.getString(R.string.money_from_gambling), 0, R.drawable.ic_money));
        gameStatistics.put(StatType.MONEY_SPENT_ON_GAMBLING, new GameStatistics(res.getString(R.string.money_spent_on_gambling), 0, R.drawable.ic_shopping));
        gameStatistics.put(StatType.GAMBLING_BALANCE, new GameStatistics(res.getString(R.string.gambling_balance), 0, R.drawable.ic_balance));
        gameStatistics.put(StatType.TOTAL_INVESTMENT_LEVELS, new GameStatistics(res.getString(R.string.total_investment_levels), 0, R.drawable.ic_investment));
        gameStatistics.put(StatType.UPGRADES_BOUGHT, new GameStatistics(res.getString(R.string.upgrades_bought), 0, R.drawable.ic_upgrade));
        gameStatistics.put(StatType.FIRST_DOLLAR, new GameStatistics(res.getString(R.string.first_dollar), new Date().getTime(), R.drawable.ic_money));
        gameStatistics.put(StatType.HIGHEST_MONEY, new GameStatistics(res.getString(R.string.highest_money), 0, R.drawable.ic_money));
        return gameStatistics;
    }
}
