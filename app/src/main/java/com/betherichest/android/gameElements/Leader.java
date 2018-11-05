package com.betherichest.android.gameElements;

public class Leader extends GameElement {
    private boolean isPlayer = false;
    private double money;

    public Leader(String name, long money) {
        this.name = name;
        this.money = money;
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
}
