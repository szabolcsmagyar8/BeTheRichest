package com.betherichest.android;

public abstract class GameElement {
    protected int id;
    protected String name;
    protected double price;
    protected int imageResource;

    protected String description;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= price;
    }
}
