package com.betherichest.android;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;

import com.betherichest.android.Factories.InvestmentFactory;
import com.betherichest.android.Factories.UpgradeFactory;
import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.GameElements.InvestmentUpgrade;
import com.betherichest.android.GameElements.TapUpgrade;
import com.betherichest.android.GameElements.Upgrade;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Szabi on 2018.03.04..
 */

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
    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);
    private static GameState gameState;

    public Handler handler;

    public MoneyChangedListener moneyChangedListener;
    public AdapterRefreshListener adapterRefreshListener;
    ArrayList<Upgrade> purchasedTapUpgrades = new ArrayList<>();


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
        gameState = new GameState();
        handler = new Handler(Looper.getMainLooper());
        InitializeEventListeners();
        StartTimer();
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
    //endregion

    public void dollarClick() {
        currentMoney += moneyPerTap;
    }

    private void StartTimer() {
        T.schedule(new TimerTask() {
            @Override
            public void run() {
                earnMoney(getMoneyPerSec() / FPS);
            }
        }, 0, 1000 / FPS);

        T.schedule(new TimerTask() {
            @Override
            public void run() {
                postAdapterRefreshRequest();    // the adapter need to be refreshed continously, providing a constant update in availability colors and displayable elements in the list
            }
        }, 0, 300);
    }


    private void deduceMoney(double price) {
        currentMoney -= price;
    }

    //region EVENTHANDLERS
    private void InitializeEventListeners() {
        moneyChangedListener = new MoneyChangedListener() {
            @Override
            public void onMoneyChanged() {
                GUIManager.getInstance().setMainUITexts();
            }
        };
    }

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

    private void earnMoney(double money) {
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
        for (Upgrade tapUpgrade: purchasedTapUpgrades) {
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
                }
            }
        }
    }

    public void loadUpgrades(List<Upgrade> savedUpgrades) {
        for (Upgrade upgrade : upgrades) {
            for (Upgrade savedUpgrade : savedUpgrades) {
                if (upgrade.getId() == savedUpgrade.getId()) {
                    upgrade.setPurchased(true);
                }
            }
        }
    }
}
