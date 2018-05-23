package com.betherichest.android;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

    Timer T = new Timer();

    private HashMap<Integer, Investment> investments;
    private HashMap<Integer, Upgrade> upgrades;

    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

    public Handler handler;
    public MoneyChangedListener moneyChangedListener;
    public AdapterRefreshListener adapterRefreshListener;

    //endregion

    //region CONSTRUCTORS
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Game() {
        investments = InvestmentFactory.createInvestments();
        upgrades = UpgradeFactory.createUpgrades(getInvestments());

        handler = new Handler(Looper.getMainLooper());
        InitializeEventListeners();
        StartTimer();
    }
    //endregion

    //region PROPERTIES
    public double getCurrentMoney() {
        return currentMoney;
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
        ArrayList<Investment> list = new ArrayList<>(investments.values());
        Collections.sort(list, new Comparator<Investment>() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Investment o1, Investment o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
        return list;
    }

    public List<Upgrade> getUpgrades() {
        ArrayList<Upgrade> list = new ArrayList<>(upgrades.values());
        Collections.sort(list, new Comparator<Upgrade>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Upgrade o1, Upgrade o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
        return list;
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
                postAdapterRefreshRequest();
            }
        }, 0, 300);
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

    private void InitializeEventListeners() {
        moneyChangedListener = new MoneyChangedListener() {
            @Override
            public void onMoneyChanged() {
                GUIManager.getInstance().setMainUITexts();
            }
        };
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

    private void earnMoney(double money) {
        currentMoney += money;
        postMoneyChanged();
    }

    private void deduceMoney(double price) {
        currentMoney -= price;
    }

    public void buyInvestment(Investment selectedInvestment) {
        deduceMoney(selectedInvestment.getPrice());
        selectedInvestment.increaseRank();
        recalculateAcquirableMoney();
    }

    public void buyUpgrade(Upgrade selectedUpgrade) {
        deduceMoney(selectedUpgrade.getPrice());
        recalculateAcquirableMoney();
    }

    private void recalculateAcquirableMoney() {
        double sum = START_MONEY_PER_SEC;
        for (Investment investment : investments.values()) {
            sum += investment.getMoneyPerSec();
        }
        moneyPerSec = sum;

        if (moneyChangedListener != null) {
            moneyChangedListener.onMoneyChanged();
        }
    }

    public void cheat() {
        moneyPerSec *= 10;
    }
}
