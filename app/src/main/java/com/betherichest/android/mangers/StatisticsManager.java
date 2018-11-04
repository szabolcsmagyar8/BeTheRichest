package com.betherichest.android.mangers;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.betherichest.android.StatType;
import com.betherichest.android.factories.StatisticsFactory;
import com.betherichest.android.gameElements.GameStatistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

public class StatisticsManager extends Observable {
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

    public void initializeBasicStats() {
       // getStatByType(StatType.TOTAL_INVESTMENT_LEVELS).setValue(Game.getInstance().getTotalInvestmentLevels());
       // getStatByType(StatType.UPGRADES_BOUGHT).setValue(Game.getInstance().getPurchasedUpgrades().size());
       // onNotify(StatType.TOTAL_INVESTMENT_LEVELS);
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

    public GameStatistics getStatByType(StatType type) {
        return gameStatistics.get(type);
    }

    public void dollarClick(double moneyPerTap) {
        getStatByType(StatType.TOTAL_CLICKS).increaseValueByOne();
        getStatByType(StatType.TOTAL_MONEY_FROM_CLICKS).increaseValueByAmount(moneyPerTap);
        onNotify(StatType.TOTAL_CLICKS);
    }

    public void earnMoney(double moneyPerSec) {
        getStatByType(StatType.TOTAL_MONEY_COLLECTED).increaseValueByAmount(moneyPerSec);
        onNotify(StatType.TOTAL_MONEY_COLLECTED);
    }

    public void buyItem(double price) {
        getStatByType(StatType.MONEY_SPENT).increaseValueByAmount(price);
    }

    public void buyInvestment() {
        getStatByType(StatType.TOTAL_INVESTMENT_LEVELS).increaseValueByOne();
        onNotify(StatType.TOTAL_INVESTMENT_LEVELS);
    }

    public void buyUpgrade() {
        getStatByType(StatType.UPGRADES_BOUGHT).increaseValueByOne();
        onNotify(StatType.UPGRADES_BOUGHT);
    }

    public void addSecond() {
        getStatByType(StatType.TOTAL_PLAYING_TIME).increaseValueByOne();
        onNotify(StatType.TOTAL_PLAYING_TIME);
    }

    public void gamble(double price) {
        getStatByType(StatType.TOTAL_GAMBLING).increaseValueByOne();
        getStatByType(StatType.MONEY_SPENT_ON_GAMBLING).increaseValueByAmount(price);
    }

    public void gamblingWin(int wonMoney) {
       getStatByType(StatType.GAMBLING_WINS).increaseValueByOne();
       getStatByType(StatType.MONEY_FROM_GAMBLING).increaseValueByAmount(wonMoney);
       getStatByType(StatType.GAMBLING_BALANCE).setValue(gameStatistics.get(StatType.MONEY_FROM_GAMBLING).getValue() - getStatByType(StatType.MONEY_SPENT_ON_GAMBLING).getValue());
    }

    public void gamblingLose() {
        getStatByType(StatType.GAMBLING_LOSES).increaseValueByOne();
        getStatByType(StatType.GAMBLING_BALANCE).setValue(gameStatistics.get(StatType.MONEY_FROM_GAMBLING).getValue() - getStatByType(StatType.MONEY_SPENT_ON_GAMBLING).getValue());
    }

    public void firstDollarClick() {
        getStatByType(StatType.FIRST_DOLLAR).setValue((new Date().getTime()));
    }

    public void setMaxCurrentMoney(double maxCurrentMoney) {
        getStatByType(StatType.HIGHEST_MONEY).setValue(maxCurrentMoney);
    }

    public void videoWatched() {
        getStatByType(StatType.VIDEOS_WATCHED).increaseValueByOne();
        getStatByType(StatType.MONEY_FROM_VIDEOS).increaseValueByAmount(Game.getInstance().getAdReward());
    }

    private void onNotify(StatType statType) {
        setChanged();
        notifyObservers(statType);
    }
}
