package com.betherichest.android.gameElements;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

@Entity
public class Leader extends GameElement {
    @Ignore
    private static int currentId = 0;

    @Ignore
    private boolean isPlayer = false;

    @ColumnInfo(name = "money")
    private double money;

    @Ignore
    private double growthRate;

    @Ignore
    public Leader() {
        this.id = currentId++;
    }

    @Ignore
    public Leader(String name, double money, boolean isPlayer) {
        this.id = currentId++;
        this.name = name;
        this.money = money;
        this.isPlayer = isPlayer;
    }

    public Leader(int id, double money) {
        this.id = id;
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getGrowthRate() {
        return growthRate;
    }
}
