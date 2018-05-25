package com.betherichest.android;

/**
 * Created by Szabi on 2018.03.22..
 */

public class Upgrade {
    static int currentId = 0;
    private int id;

    private double price;
    private int multiplierEffect;
    private int imageResource;
    private int color;
    private boolean displayable;
    private boolean purchased = false;
    private String description;

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

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isDisplayable() {
        return !purchased;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= getPrice();
    }
}
