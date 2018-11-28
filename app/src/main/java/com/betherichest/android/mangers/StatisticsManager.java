package com.betherichest.android.mangers;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.betherichest.android.StatType;
import com.betherichest.android.connection.RequestParam;
import com.betherichest.android.factories.StatisticsFactory;
import com.betherichest.android.gameElements.GameStatistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class StatisticsManager extends Observable {
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

    public GameStatistics getStatByType(StatType type) {
        return gameStatistics.get(type);
    }

    public void dollarClick(double moneyPerTap) {
        getStatByType(StatType.TOTAL_CLICKS).increaseValueByOne();
        getStatByType(StatType.MONEY_FROM_CLICKS).increaseValueByAmount(moneyPerTap);
        onNotify(StatType.TOTAL_CLICKS);
    }

    public void earnMoney(double moneyPerSec) {
        getStatByType(StatType.TOTAL_MONEY_COLLECTED).increaseValueByAmount(moneyPerSec);
    }

    public void buyItem(double price) {
        getStatByType(StatType.MONEY_SPENT).increaseValueByAmount(price);
    }

    public void buyInvestment() {
        getStatByType(StatType.TOTAL_INVESTMENT_LEVELS).increaseValueByOne();
        onNotify(StatType.TOTAL_INVESTMENT_LEVELS);
    }

    public void earnInvestmentMoney(double money) {
        getStatByType(StatType.MONEY_FROM_INVESTMENTS).increaseValueByAmount(money);
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
        onNotify(StatType.TOTAL_GAMBLING);
    }

    public void gamblingWin(double wonMoney) {
        getStatByType(StatType.GAMBLING_WINS).increaseValueByOne();
        getStatByType(StatType.MONEY_FROM_GAMBLING).increaseValueByAmount(wonMoney);
        getStatByType(StatType.GAMBLING_BALANCE).setValue(gameStatistics.get(StatType.MONEY_FROM_GAMBLING).getValue() - getStatByType(StatType.MONEY_SPENT_ON_GAMBLING).getValue());
        onNotify(StatType.MONEY_FROM_GAMBLING);
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
        onNotify(StatType.VIDEOS_WATCHED);
    }

    protected void onNotify(Object value) {
        setChanged();
        notifyObservers(value);
    }

    public List<RequestParam> getStatRequestParams() {
        List<RequestParam> params = new LinkedList<>();
        params.add(new RequestParam("currentMoney", String.valueOf(Game.getInstance().getCurrentMoney())));
        params.add(new RequestParam("currentClicks", String.valueOf(getStatByType(StatType.TOTAL_CLICKS).getValue())));
        params.add(new RequestParam("currentPlaytime", String.valueOf(getStatByType(StatType.TOTAL_PLAYING_TIME).getValue())));
        params.add(new RequestParam("totalMoneyCollected", String.valueOf(getStatByType(StatType.TOTAL_MONEY_COLLECTED).getValue())));
        params.add(new RequestParam("totalMoneySpent", String.valueOf(getStatByType(StatType.MONEY_SPENT).getValue())));
        params.add(new RequestParam("totalInvestmentLevels", String.valueOf(getStatByType(StatType.TOTAL_INVESTMENT_LEVELS).getValue())));
        params.add(new RequestParam("upgradesBought", String.valueOf(getStatByType(StatType.UPGRADES_BOUGHT).getValue())));
        params.add(new RequestParam("moneyFromInvestments", String.valueOf(getStatByType(StatType.MONEY_FROM_INVESTMENTS).getValue())));
        params.add(new RequestParam("achievementsUnlocked", String.valueOf(getStatByType(StatType.ACHIEVEMENTS_UNLOCKED).getValue())));
        params.add(new RequestParam("moneyFromVideos", String.valueOf(getStatByType(StatType.MONEY_FROM_VIDEOS).getValue())));
        params.add(new RequestParam("moneyFromGambling", String.valueOf(getStatByType(StatType.MONEY_FROM_GAMBLING).getValue())));
        params.add(new RequestParam("moneyFromClicks", String.valueOf(getStatByType(StatType.MONEY_FROM_CLICKS).getValue())));
        params.add(new RequestParam("highestMoney", String.valueOf(getStatByType(StatType.HIGHEST_MONEY).getValue())));
        params.add(new RequestParam("videosWatched", String.valueOf(getStatByType(StatType.VIDEOS_WATCHED).getValue())));
        params.add(new RequestParam("moneySpentGambling", String.valueOf(getStatByType(StatType.MONEY_SPENT_ON_GAMBLING).getValue())));
        params.add(new RequestParam("gamblingBalance", String.valueOf(getStatByType(StatType.GAMBLING_BALANCE).getValue())));
        return params;
    }

}
