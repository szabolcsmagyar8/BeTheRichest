package com.betherichest.android;

/**
 * Created by Szabi on 2018.03.22..
 */

public class Upgrade {
    static int currentId = 0;

    private int id;
    private double price;
    private int imageResource;
    private int color;
    private boolean isDisplayable;
    private String description;

    public Upgrade(String description, double price, int imageResource, int color) {
        this.id = currentId++;
        this.description = description;
        this.price = price;
        this.imageResource = imageResource;
        this.color = color;
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

    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= getPrice();
    }
}
