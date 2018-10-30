package com.betherichest.android.mangers;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;

import com.betherichest.android.GameState;
import com.betherichest.android.factories.BoostersFactory;
import com.betherichest.android.factories.GamblingFactory;
import com.betherichest.android.factories.InvestmentFactory;
import com.betherichest.android.factories.UpgradeFactory;
import com.betherichest.android.gameElements.Booster;
import com.betherichest.android.gameElements.Gambling;
import com.betherichest.android.gameElements.GameStatistics;
import com.betherichest.android.gameElements.GlobalIncrementUpgrade;
import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.gameElements.InvestmentUpgrade;
import com.betherichest.android.gameElements.TapUpgrade;
import com.betherichest.android.gameElements.Upgrade;
import com.betherichest.android.listenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.listenerInterfaces.MoneyChangedListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    //region VARIABLES
    private static Game instance;

    private double START_MONEY_PER_TAP = 1d;
    private double START_MONEY_PER_SEC = 0d;

    private double currentMoney = 0d;
    private double moneyPerTap = 1d;
    private double moneyPerSec = 0d;

    public static final Integer FPS = 24;
    public static final double SEC_TO_HOUR_MULTIPLIER = 3600;
    private static final double AD_REWARD_MULTIPLIER = 220;

    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

    private Timer T = new Timer();

    private List<Investment> investments;
    private List<Upgrade> upgrades;
    private List<Gambling> gamblings;
    private List<Booster> boosters;

    private static boolean timerPaused;

    public static GameState gameState;
    public static StatisticsManager statisticsManager;

    public Handler handler;
    public MoneyChangedListener moneyChangedListener;
    public AdapterRefreshListener adapterRefreshListener;
    private static boolean gamblingAnimationRunning = false;
    public AdapterRefreshListener slowAdapterRefreshListener;
    //endregion

    //region CONSTRUCTORS
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Game() {
        investments = InvestmentFactory.getCreatedInvestments();
        UpgradeFactory.createUpgrades(investments);
        upgrades = UpgradeFactory.getCreatedUpgrades();
        gamblings = GamblingFactory.getCreatedGamblings();
        boosters = BoostersFactory.getCreatedBoosters();

        handler = new Handler(Looper.getMainLooper());

        statisticsManager = StatisticsManager.getInstance();
        gameState = new GameState();

        startTimer();
    }
    //endregion

    //region PROPERTIES
    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setMoneyPerTap(double moneyPerTap) {
        this.moneyPerTap = moneyPerTap;
    }

    public void setMoneyPerSec(double moneyPerSec) {
        this.moneyPerSec = moneyPerSec;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public String getCurrentMoneyAsString() {
        return nf.format(Math.round(currentMoney));
    }

    public double getMoneyPerTap() {
        return moneyPerTap;
    }

    public String getMoneyPerTapAsString() {
        nf.setMaximumFractionDigits(1);
        return String.format("%s $ per tap", nf.format(moneyPerTap));
    }

    public double getMoneyPerSec() {
        return moneyPerSec;
    }

    public String getMoneyPerSecAsString() {
        nf.setMaximumFractionDigits(1);
        return nf.format(moneyPerSec) + " $ per sec";
    }

    public List<Investment> getInvestments() {
        Collections.sort(investments, new Comparator<Investment>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Investment o1, Investment o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        return investments;
    }

    public List<Upgrade> getUpgrades() {
        Collections.sort(upgrades, new Comparator<Upgrade>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Upgrade o1, Upgrade o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
        return upgrades;
    }

    public static void setTimerPaused(boolean paused) {
        timerPaused = paused;
    }

    public List<Upgrade> getDisplayableUpgrades() {
        ArrayList<Upgrade> displayableUpgrades = new ArrayList<>();
        for (Upgrade upgrade : getUpgrades()) {
            if (upgrade.isDisplayable()) {
                displayableUpgrades.add(upgrade);

            }
        }
        return displayableUpgrades;
    }

    public double getDPSPercentage(Investment investment) {
        return getMoneyPerSec() == 0 ? 0 : investment.getMoneyPerSec() / getMoneyPerSec() * 100;
    }

    public double getTotalInvestmentLevels() {
        double sum = 0;
        for (Investment inv : investments) {
            sum += inv.getLevel();
        }
        return sum;
    }

    public List<Gambling> getGamblings() {
        return gamblings;
    }

    public List<Booster> getBoosters() {
        return boosters;
    }

    public Booster getBoosterBySkuId(String selectedSKU) {
        for (Booster booster : boosters) {
            if (booster.getSkuId() == selectedSKU) {
                return booster;
            }
        }
        return null;
    }

    public static boolean isGamblingAnimationRunning() {
        return gamblingAnimationRunning;
    }

    public static void setGamblingAnimationRunning(boolean gamblingAnimationRunning) {
        Game.gamblingAnimationRunning = gamblingAnimationRunning;
    }

    public List<Upgrade> getPurchasedUpgrades() {
        List<Upgrade> purchasedUpgrades = new ArrayList<>();
        for (Upgrade upgrade : upgrades) {
            if (upgrade.isPurchased()) {
                purchasedUpgrades.add(upgrade);
            }
        }
        return purchasedUpgrades;
    }

    public double getAdReward() {
        return moneyPerSec * AD_REWARD_MULTIPLIER;
    }
    //endregion

    //region EVENTHANDLERS
    private void postAdapterRefreshRequest() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (adapterRefreshListener != null) {
                    adapterRefreshListener.refreshAdapter();
                }
            }
        });
    }

    private void postSlowAdapterRefreshRequest() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (slowAdapterRefreshListener != null) {
                    slowAdapterRefreshListener.refreshAdapter();
                }
            }
        });
    }

    private void postMoneyChanged() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (moneyChangedListener != null) {
                    moneyChangedListener.onMoneyChanged();
                }
            }
        });
    }
    //endregion

    public void startTimer() {
        T.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!timerPaused) {
                    earnMoney(getMoneyPerSec() / FPS);
                    postAdapterRefreshRequest();   // the adapters need to be refreshed continuously, providing a constant update in availability colors and displayable elements in the list
                }
            }
        }, 0, 1000 / FPS);

        T.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!timerPaused) {
                    statisticsManager.addSecond();    // counts the elapsed seconds in the game
                }
            }
        }, 0, 1000);

        T.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!timerPaused) {
                    postSlowAdapterRefreshRequest();   // the adapters need to be refreshed continuously, providing a constant update in availability colors and displayable elements in the list
                }
            }
        }, 0, 400);
    }

    private void deduceMoney(double price) {
        currentMoney -= price;
    }

    public void dollarClick() {
        earnMoney(moneyPerTap);
        statisticsManager.dollarClick(moneyPerTap);
        if (gameState.isFirstDollarClick()) {
            statisticsManager.firstDollarClick();
            gameState.setFirstDollarClick(false);
        }
    }

    public void earnMoney(double money) {
        currentMoney += money;
        if (currentMoney > gameState.getMaxCurrentMoney()) {
            gameState.setMaxCurrentMoney(currentMoney);
            statisticsManager.setMaxCurrentMoney(currentMoney);
        }

        statisticsManager.earnMoney(money);
        postMoneyChanged();
    }

    public void buyInvestment(Investment selectedInvestment) {
        deduceMoney(selectedInvestment.getPrice());
        statisticsManager.buyItem(selectedInvestment.getPrice());
        selectedInvestment.increaseLevel();
        recalculateMoneyPerSecond();
        recalculateMoneyPerTap();
    }

    public void buyUpgrade(Upgrade selectedUpgrade) {
        selectedUpgrade.setPurchased(true);
        deduceMoney(selectedUpgrade.getPrice());

        if (selectedUpgrade instanceof InvestmentUpgrade) {
            ((InvestmentUpgrade) selectedUpgrade).getRelevantInvestment().addPurchasedRelevantUpgrade(selectedUpgrade); // to store the purchased upgrades in a separate list for every investment instance
            recalculateMoneyPerSecond();
        }
        if (selectedUpgrade instanceof TapUpgrade) {
            recalculateMoneyPerTap();
        }

        if (selectedUpgrade instanceof GlobalIncrementUpgrade) {
            recalculateMoneyPerTap();
        }

        statisticsManager.buyItem(selectedUpgrade.getPrice());
    }

    public void buyGambling(Gambling selectedGambling) {
        deduceMoney(selectedGambling.getPrice());
        statisticsManager.buyItem(selectedGambling.getPrice());
        statisticsManager.gamble(selectedGambling.getPrice());
    }

    private void recalculateMoneyPerSecond() {
        double sum = START_MONEY_PER_SEC;
        for (Investment investment : investments) {
            sum += investment.getMoneyPerSec();
        }

        moneyPerSec = sum;

        if (moneyChangedListener != null) {
            moneyChangedListener.onMoneyChanged();
        }
    }

    private void recalculateMoneyPerTap() {
        double sum = START_MONEY_PER_TAP;

        for (Upgrade upgrade : getPurchasedUpgrades()) {
            if (upgrade.isPurchased() && upgrade instanceof GlobalIncrementUpgrade) {
                double globalIncrementReward = getTotalInvestmentLevels() * upgrade.getMultiplier();
                sum += globalIncrementReward;
            }
            if (upgrade.isPurchased() && upgrade instanceof TapUpgrade) {
                sum *= upgrade.getMultiplier();
            }
        }

        moneyPerTap = sum;

        if (moneyChangedListener != null) {
            moneyChangedListener.onMoneyChanged();
        }
    }

    public void cheat() {
        moneyPerSec += 10;
        moneyPerSec *= 10;
    }

    public void loadInvestments(List<Investment> savedInvestments) {
        for (Investment investment : investments) {
            for (Investment savedInvestment : savedInvestments) {
                if (investment.getId() == savedInvestment.getId()) {
                    investment.setLevel(savedInvestment.getLevel());
                    break;
                }
            }
        }
    }

    public void loadUpgrades(List<Upgrade> savedUpgrades) {
        for (Upgrade savedUpgrade : savedUpgrades) {
            for (Upgrade upgrade : upgrades) {
                if (upgrade.getId() == savedUpgrade.getId()) {
                    upgrade.setPurchased(true);
                    if (upgrade instanceof InvestmentUpgrade) {
                        Investment inv = ((InvestmentUpgrade) upgrade).getRelevantInvestment();
                        if (!inv.getPurchasedRelevantUpgrades().contains(upgrade)) {
                            inv.addPurchasedRelevantUpgrade(upgrade);
                        }
                    }
                    break;
                }
            }
        }
    }

    public void loadGameStatistics(List<GameStatistics> savedGameStatistics) {
        for (GameStatistics savedStat : savedGameStatistics) {
            for (GameStatistics stat : statisticsManager.getGameStatistics()) {
                if (stat.getId() == savedStat.getId()) {
                    stat.setValue(savedStat.getValue());
                }
            }
        }
    }
}