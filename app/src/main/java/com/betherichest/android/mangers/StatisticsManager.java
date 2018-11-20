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
import java.util.Map;
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

    public Map<String, Object> getStatRequestParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("currentMoney", Game.getInstance().getCurrentMoney());
        params.put("currentClicks", getStatByType(StatType.TOTAL_CLICKS).getValue());
        params.put("currentPlaytime", getStatByType(StatType.TOTAL_PLAYING_TIME).getValue());
        params.put("totalMoneyCollected", getStatByType(StatType.TOTAL_MONEY_COLLECTED).getValue());
        params.put("totalMoneySpent", getStatByType(StatType.MONEY_SPENT).getValue());
        params.put("totalInvestmentLevels", getStatByType(StatType.TOTAL_INVESTMENT_LEVELS).getValue());
        params.put("upgradesBought", getStatByType(StatType.UPGRADES_BOUGHT).getValue());
        params.put("moneyFromInvestments", getStatByType(StatType.MONEY_FROM_INVESTMENTS).getValue());
        params.put("achievementsUnlocked", getStatByType(StatType.ACHIEVEMENTS_UNLOCKED).getValue());
        params.put("moneyFromVideos", getStatByType(StatType.MONEY_FROM_VIDEOS).getValue());
        params.put("moneyFromGambling", getStatByType(StatType.MONEY_FROM_GAMBLING).getValue());
        params.put("moneyFromClicks", getStatByType(StatType.MONEY_FROM_CLICKS).getValue());
        params.put("highestMoney", getStatByType(StatType.HIGHEST_MONEY).getValue());
        params.put("videosWatched", getStatByType(StatType.VIDEOS_WATCHED).getValue());
        params.put("moneySpentGambling", getStatByType(StatType.MONEY_SPENT_ON_GAMBLING).getValue());
        params.put("gamblingBalance", getStatByType(StatType.GAMBLING_BALANCE).getValue());
        return params;
    }

}
