package com.betherichest.android;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class GameState {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "current_money")
    private double currentMoney = 0d;

    @ColumnInfo(name = "money_per_tap")
    private double moneyPerTap = 1d;

    @ColumnInfo(name = "money_per_sec")
    private double moneyPerSec = 0d;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public double getMoneyPerTap() {
        return moneyPerTap;
    }

    public void setMoneyPerTap(double moneyPerTap) {
        this.moneyPerTap = moneyPerTap;
    }

    public double getMoneyPerSec() {
        return moneyPerSec;
    }

    public void setMoneyPerSec(double moneyPerSec) {
        this.moneyPerSec = moneyPerSec;
    }
}
