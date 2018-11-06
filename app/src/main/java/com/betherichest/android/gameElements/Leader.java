package com.betherichest.android.gameElements;

public class Leader extends GameElement {
    private boolean isPlayer = false;
    private double money;

    private double growthRate;

    public Leader(String name, double money, double growthRate) {
        this.name = name;
        this.money = money;
        this.growthRate = growthRate;
    }

    public Leader(String name, double money, boolean isPlayer) {
        this.name = name;
        this.money = money;
        this.isPlayer = isPlayer;
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
