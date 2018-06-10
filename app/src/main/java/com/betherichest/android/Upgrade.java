package com.betherichest.android;

public abstract class Upgrade extends GameElement{
    static int currentId = 0;
    private int multiplier;
    private int color;
    private boolean displayable;
    protected boolean purchased = false;


    public Upgrade(double price, int multiplier, int color) {
        this.id = currentId++;
        this.price = price;
        this.multiplier = multiplier;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public abstract boolean isDisplayable();

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
