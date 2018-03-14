package com.betherichest.android;

import android.os.Build;
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
    private static Game instance;

    private double currentMoney = 0d;
    private double moneyPerTap = 1d;
    private double moneyPerSec = 0d;

    public static Integer FPS = 24;

    Timer T = new Timer();

    private HashMap<Integer, Investment> investments;

    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);


    public static Game Get() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Game() {
        StartTimer();
        //FactoryManager.StartProduction();
        investments = InvestmentFactory.createInvestments();
    }

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

    public void dollarClick() {
        currentMoney += moneyPerTap;
    }

    public void investmentClick() {

    }

    private void StartTimer() {
        T.schedule(new TimerTask() {
            @Override
            public void run() {
                earnMoney(getMoneyPerSec() / FPS);

            }
        }, 0, 1000 / FPS);

    }

    private void earnMoney(double money) {
        currentMoney += money;
    }

    private void deduceMoney(double price) {
        currentMoney -= price;
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

    public void buyInvestment(Investment selectedInvestment) {
        deduceMoney(selectedInvestment.getPrice());
        selectedInvestment.increaseRank();
    }
}
