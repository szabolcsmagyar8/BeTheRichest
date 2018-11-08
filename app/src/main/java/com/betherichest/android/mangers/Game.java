package com.betherichest.android.mangers;

import android.os.Handler;
import android.os.Looper;

import com.betherichest.android.App;
import com.betherichest.android.GameState;
import com.betherichest.android.StatType;
import com.betherichest.android.factories.AchievementFactory;
import com.betherichest.android.factories.BoosterFactory;
import com.betherichest.android.factories.GamblingFactory;
import com.betherichest.android.factories.InvestmentFactory;
import com.betherichest.android.factories.LeaderFactory;
import com.betherichest.android.factories.UpgradeFactory;
import com.betherichest.android.gameElements.Booster;
import com.betherichest.android.gameElements.Gambling;
import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.gameElements.Leader;
import com.betherichest.android.gameElements.achievement.Achievement;
import com.betherichest.android.gameElements.upgrade.GlobalIncrementUpgrade;
import com.betherichest.android.gameElements.upgrade.InvestmentUpgrade;
import com.betherichest.android.gameElements.upgrade.TapUpgrade;
import com.betherichest.android.gameElements.upgrade.Upgrade;
import com.betherichest.android.listenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.listenerInterfaces.MoneyChangedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    public static final long FPS = 25;
    public static final double SEC_TO_HOUR_MULTIPLIER = 3600;
    private static final double AD_REWARD_MULTIPLIER = 220;

    private Timer T = new Timer();
    private static boolean timerPaused;

    private List<Investment> investments;
    private List<Upgrade> upgrades;
    private List<Gambling> gamblings;
    private List<Booster> boosters;
    private List<Achievement> achievements;
    private List<Leader> leaders;

    public static GameState gameState;
    public static StatisticsManager statisticsManager;
    public static AchievementManager achievementManager;

    public Handler handler;
    public MoneyChangedListener moneyChangedListener;
    public AdapterRefreshListener smoothAdapterRefreshListener;
    public AdapterRefreshListener slowAdapterRefreshListener;
    private static boolean gamblingAnimationRunning = false;
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
        upgrades = UpgradeFactory.getCreatedUpgrades(investments);
        gamblings = GamblingFactory.getCreatedGamblings();
        boosters = BoosterFactory.getCreatedBoosters();
        achievements = AchievementFactory.getCreatedAchievements();
        leaders = LeaderFactory.getLeaders();

        statisticsManager = new StatisticsManager();
        achievementManager = new AchievementManager(achievements);
        gameState = new GameState();

        handler = new Handler(Looper.getMainLooper());
        startTimer();
    }
    //endregion

    //region PROPERTIES
    public double getCurrentMoney() {
        return currentMoney;
    }

    public String getCurrentMoneyAsString() {
        return App.NF.format(Math.round(currentMoney));
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public double getMoneyPerSec() {
        return moneyPerSec;
    }

    public String getMoneyPerSecAsString() {
        App.NF.setMaximumFractionDigits(1);
        return App.NF.format(moneyPerSec) + " $ per sec";
    }

    public void setMoneyPerSec(double moneyPerSec) {
        this.moneyPerSec = moneyPerSec;
    }

    public double getMoneyPerTap() {
        return moneyPerTap;
    }

    public String getMoneyPerTapAsString() {
        App.NF.setMaximumFractionDigits(1);
        return String.format("%s $ per tap", App.NF.format(moneyPerTap));
    }

    public void setMoneyPerTap(double moneyPerTap) {
        this.moneyPerTap = moneyPerTap;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public List<Upgrade> getUpgrades() {
        Collections.sort(upgrades, new Comparator<Upgrade>() {
            @Override
            public int compare(Upgrade o1, Upgrade o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
        return upgrades;
    }

    public List<Gambling> getGamblings() {
        return gamblings;
    }

    public List<Leader> getLeaders() {
        return leaders;
    }

    public List<Booster> getBoosters() {
        return boosters;
    }

    public List<Achievement> getAchievements() {
        return achievements;
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

    public List<Upgrade> getPurchasedUpgrades() {
        List<Upgrade> purchasedUpgrades = new ArrayList<>();
        for (Upgrade upgrade : upgrades) {
            if (upgrade.isPurchased()) {
                purchasedUpgrades.add(upgrade);
            }
        }
        return purchasedUpgrades;
    }

    public static void setTimerPaused(boolean paused) {
        timerPaused = paused;
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

    public double getAdReward() {
        return moneyPerSec * AD_REWARD_MULTIPLIER;
    }
    //endregion

    //region EVENTHANDLERS
    private void postAdapterRefreshRequest() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (smoothAdapterRefreshListener != null) {
                    smoothAdapterRefreshListener.refreshAdapter();
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

    private void startTimer() {
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
                    statisticsManager.onNotify(StatType.TOTAL_MONEY_COLLECTED);
                }
            }
        }, 0, 1000);

        T.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!timerPaused) {
                    enrichLeaders();
                    postSlowAdapterRefreshRequest();   // the adapters need to be refreshed continuously, providing a constant update in availability colors and displayable elements in the list
                }
            }
        }, 0, 500);
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

    public void buyInvestment(Investment selectedInvestment) {
        deduceMoney(selectedInvestment.getPrice());
        statisticsManager.buyItem(selectedInvestment.getPrice());
        statisticsManager.buyInvestment();
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
        statisticsManager.buyUpgrade();
    }

    public void buyGambling(Gambling selectedGambling) {
        deduceMoney(selectedGambling.getPrice());
        statisticsManager.buyItem(selectedGambling.getPrice());
        statisticsManager.gamble(selectedGambling.getPrice());
    }

    private void enrichLeaders() {
        for (Leader leader : leaders) {
            if (leader.isPlayer()) {
                leader.setMoney(currentMoney);
            } else {
                leader.setMoney(leader.getMoney() + (leader.getMoney() * (leader.getGrowthRate() - 1)) / 100000);
            }
        }
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
}