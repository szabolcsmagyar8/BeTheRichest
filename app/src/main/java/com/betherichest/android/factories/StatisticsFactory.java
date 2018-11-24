package com.betherichest.android.factories;

import android.content.res.Resources;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.StatType;
import com.betherichest.android.gameElements.GameStatistics;

import java.util.Date;
import java.util.HashMap;


public class StatisticsFactory {
    public static HashMap<StatType, GameStatistics> initializeGameStatistics() {
        HashMap<StatType, GameStatistics> gameStatistics = new HashMap<>();
        Resources res = App.getContext().getResources();

        gameStatistics.put(StatType.TOTAL_MONEY_COLLECTED, new GameStatistics(res.getString(R.string.total_money_collected), 0.0, R.drawable.dollarsmall));
        gameStatistics.put(StatType.TOTAL_CLICKS, new GameStatistics(res.getString(R.string.total_clicks), 0, R.drawable.click));
        gameStatistics.put(StatType.MONEY_FROM_CLICKS, new GameStatistics(res.getString(R.string.total_money_from_clicks), 0.0, R.drawable.moneyclick));
        gameStatistics.put(StatType.MONEY_SPENT, new GameStatistics(res.getString(R.string.money_spent), 0, R.drawable.moneyspent));
        gameStatistics.put(StatType.MONEY_FROM_INVESTMENTS, new GameStatistics(res.getString(R.string.money_from_investments), 0.0, R.drawable.moneyfrominvestment));
        gameStatistics.put(StatType.TOTAL_PLAYING_TIME, new GameStatistics(res.getString(R.string.total_playing_time), 0, R.drawable.stopwatch));
        gameStatistics.put(StatType.TOTAL_GAMBLING, new GameStatistics(res.getString(R.string.total_gambling), 0, R.mipmap.clover));
        gameStatistics.put(StatType.GAMBLING_WINS, new GameStatistics(res.getString(R.string.gambling_wins), 0, R.drawable.gamblingwin));
        gameStatistics.put(StatType.GAMBLING_LOSES, new GameStatistics(res.getString(R.string.gambling_loses), 0, R.drawable.gamblinglose));
        gameStatistics.put(StatType.MONEY_FROM_GAMBLING, new GameStatistics(res.getString(R.string.money_from_gambling), 0, R.drawable.clovermoney));
        gameStatistics.put(StatType.MONEY_SPENT_ON_GAMBLING, new GameStatistics(res.getString(R.string.money_spent_on_gambling), 0, R.drawable.moneyspentgambling));
        gameStatistics.put(StatType.GAMBLING_BALANCE, new GameStatistics(res.getString(R.string.gambling_balance), 0, R.drawable.gamblingbalance2));
        gameStatistics.put(StatType.TOTAL_INVESTMENT_LEVELS, new GameStatistics(res.getString(R.string.total_investment_levels), 0, R.mipmap.investments));
        gameStatistics.put(StatType.UPGRADES_BOUGHT, new GameStatistics(res.getString(R.string.upgrades_bought), 0, R.mipmap.upgrade));
        gameStatistics.put(StatType.FIRST_DOLLAR, new GameStatistics(res.getString(R.string.first_dollar), new Date().getTime(), R.drawable.firstdollar));
        gameStatistics.put(StatType.HIGHEST_MONEY, new GameStatistics(res.getString(R.string.highest_money), 0, R.drawable.dollarsmall));
        gameStatistics.put(StatType.VIDEOS_WATCHED, new GameStatistics(res.getString(R.string.videos_wathced), 0, R.drawable.video));
        gameStatistics.put(StatType.MONEY_FROM_VIDEOS, new GameStatistics(res.getString(R.string.money_from_videos), 0, R.drawable.moneyfromvideos));
        gameStatistics.put(StatType.ACHIEVEMENTS_UNLOCKED, new GameStatistics(res.getString(R.string.achievements_unlocked), 0, R.drawable.achievement));
        return gameStatistics;
    }
}
