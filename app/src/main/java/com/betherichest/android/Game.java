package com.betherichest.android;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Szabi on 2018.03.04..
 */

public class Game {
    private double currentMoney = 0d;
    private double moneyPerTap = 1d;
    private double moneyPerSec = 0d;

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
}
