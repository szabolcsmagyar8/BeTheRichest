package com.betherichest.android.gameElements;

public class Leader extends GameElement {
    private static int currentId = 0;
    private boolean isPlayer = false;
    private double money;

    private double growthRate;

    public Leader() {
        this.id = currentId++;
    }

    public Leader(String name, double money, boolean isPlayer) {
        this.id = currentId++;
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
