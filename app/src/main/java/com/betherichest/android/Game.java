package com.betherichest.android;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;

import com.betherichest.android.Factories.GamblingFactory;
import com.betherichest.android.Factories.InvestmentFactory;
import com.betherichest.android.Factories.UpgradeFactory;
import com.betherichest.android.GameElements.Gambling;
import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.GameElements.InvestmentUpgrade;
import com.betherichest.android.GameElements.TapUpgrade;
import com.betherichest.android.GameElements.Upgrade;
import com.betherichest.android.ListenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.ListenerInterfaces.MoneyChangedListener;

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
    public static Integer FPS = 24;

    private Timer T = new Timer();

    private List<Investment> investments;
    private List<Upgrade> upgrades;
    private List<Gambling> gamblings;

    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);
    private static GameState gameState;

    public Handler handler;

    public MoneyChangedListener moneyChangedListener;
    public AdapterRefreshListener adapterRefreshListener;
    ArrayList<Upgrade> purchasedTapUpgrades = new ArrayList<>();

    private boolean isTimerPaused;
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
        gamblings = GamblingFactory.getCreateGamblings();
        gameState = new GameState();
        handler = new Handler(Looper.getMainLooper());
        startTimer();
    }

    public GameState getGameState() {
        return gameState;
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
        return String.format("%s $ per tap", nf.format((int) moneyPerTap));
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

    public List<Upgrade> getDisplayableUpgrades() {
        ArrayList<Upgrade> displayableUpgrades = new ArrayList<>();
        for (Upgrade upgrade : getUpgrades()) {
            if (upgrade.isDisplayable()) {
                displayableUpgrades.add(upgrade);
            }
        }
        return displayableUpgrades;
    }

    public void setTimerPaused(boolean timerPaused) {
        isTimerPaused = timerPaused;
    }
    //endregion

    public void dollarClick() {
        currentMoney += moneyPerTap;
    }

    public void startTimer() {
        T.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isTimerPaused) {
                    earnMoney(getMoneyPerSec() / FPS);
                }
            }
        }, 0, 1000 / FPS);

        T.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isTimerPaused) {
                    postAdapterRefreshRequest();    // the adapter need to be refreshed continuously, providing a constant update in availability colors and displayable elements in the list
                }
            }
        }, 0, 300);
    }

    public void stopTimer() {
        T.cancel();
    }


    private void deduceMoney(double price) {
        currentMoney -= price;
    }

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

    public void earnMoney(double money) {
        currentMoney += money;
        postMoneyChanged();
    }

    public void buyInvestment(Investment selectedInvestment) {
        deduceMoney(selectedInvestment.getPrice());
        selectedInvestment.increaseRank();
        recalculateMoneyPerSecond();
    }

    public void buyUpgrade(Upgrade selectedUpgrade) {
        selectedUpgrade.setPurchased(true);
        deduceMoney(selectedUpgrade.getPrice());

        if (selectedUpgrade instanceof InvestmentUpgrade) {
            ((InvestmentUpgrade) selectedUpgrade).getRelevantInvestment().addPurchasedRelevantUpgrade(selectedUpgrade); // to store the purchased upgrades in a separate list for every investment instance
            recalculateMoneyPerSecond();
        }
        if (selectedUpgrade instanceof TapUpgrade) {
            purchasedTapUpgrades.add(selectedUpgrade);
            recalculateMoneyPerTap();
        }
    }

    public void buyGambling(Gambling selectedGambling) {
        deduceMoney(selectedGambling.getPrice());
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
        for (Upgrade tapUpgrade : purchasedTapUpgrades) {
            sum *= tapUpgrade.getMultiplier();
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
                    investment.setRank(savedInvestment.getRank());
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

    public List<Gambling> getGamblings() {
        return gamblings;
    }
}