package com.betherichest.android;

public abstract class Upgrade extends GameElement{
    static int currentId = 0;
    private int multiplierEffect;
    private int color;
    private boolean displayable;
    protected boolean purchased = false;


    public Upgrade(String description, double price, int multiplierEffect, int imageResource, int color) {
        this.id = currentId++;
        this.description = description;
        this.price = price;
        this.multiplierEffect = multiplierEffect;
        this.color = color;
        this.imageResource = imageResource;
    }

    public int getColor() {
        return color;
    }

    public int getMultiplierEffect() {
        return multiplierEffect;
    }

    public abstract boolean isDisplayable();

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
