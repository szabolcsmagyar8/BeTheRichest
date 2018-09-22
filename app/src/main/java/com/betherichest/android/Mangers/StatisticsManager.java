package com.betherichest.android.Mangers;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.method.DateTimeKeyListener;

import com.betherichest.android.Factories.StatisticsFactory;
import com.betherichest.android.GameElements.GameStatistics;
import com.betherichest.android.GameState;
import com.betherichest.android.StatType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StatisticsManager {
    private static StatisticsManager instance;
    private HashMap<StatType, GameStatistics> gameStatistics;

    public StatisticsManager() {
        gameStatistics = StatisticsFactory.initializeGameStatistics();
    }

    public static StatisticsManager getInstance() {
        if (instance == null) {
            instance = new StatisticsManager();
        }
        return instance;
    }

    public void initailizeBasicStats() {
        gameStatistics.get(StatType.TOTAL_INVESTMENT_LEVELS).setValue(Game.getInstance().getTotalInvestmentLevels());
        gameStatistics.get(StatType.UPGRADES_BOUGHT).setValue(Game.getInstance().getPurchasedUpgrades().size());
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
        gameStatistics.get(StatType.MONEY_SPENT).increaseValueByAmount(price);
    }

    public void addSecond() {
        gameStatistics.get(StatType.TOTAL_PLAYING_TIME).increaseValueByOne();
    }

    public void gamble(double price) {
        gameStatistics.get(StatType.TOTAL_GAMBLING).increaseValueByOne();
        gameStatistics.get(StatType.MONEY_SPENT_ON_GAMBLING).increaseValueByAmount(price);
    }

    public void gamblingWin(int wonMoney) {
        gameStatistics.get(StatType.GAMBLING_WINS).increaseValueByOne();
        gameStatistics.get(StatType.MONEY_FROM_GAMBLING).increaseValueByAmount(wonMoney);
        gameStatistics.get(StatType.GAMBLING_BALANCE).setValue(gameStatistics.get(StatType.MONEY_FROM_GAMBLING).getValue() - gameStatistics.get(StatType.MONEY_SPENT_ON_GAMBLING).getValue());
    }

    public void gamblingLose() {
        gameStatistics.get(StatType.GAMBLING_LOSES).increaseValueByOne();
        gameStatistics.get(StatType.GAMBLING_BALANCE).setValue(gameStatistics.get(StatType.MONEY_FROM_GAMBLING).getValue() - gameStatistics.get(StatType.MONEY_SPENT_ON_GAMBLING).getValue());
    }

    public void firstDollarClick() {
        gameStatistics.get(StatType.FIRST_DOLLAR).setValue((new Date().getTime()));
    }
}
