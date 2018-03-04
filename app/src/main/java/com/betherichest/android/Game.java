package com.betherichest.android;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Szabi on 2018.03.04..
 */

public class Game {
    private double currentMoney = 0d;
    private double moneyPerTap = 1d;
    private double moneyPerSec = 0d;

    public static Integer FPS = 24;
    Timer T = new Timer();

    private static Game instance;

    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);


    public static Game Get() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
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
        moneyPerSec++;
    }

    public Game() {
        StartTimer();
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
}
