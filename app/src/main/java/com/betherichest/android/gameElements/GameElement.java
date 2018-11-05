package com.betherichest.android.gameElements;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.betherichest.android.App;
import com.betherichest.android.mangers.Game;

import java.io.Serializable;

public abstract class GameElement implements Serializable{

    @PrimaryKey
    protected int id;

    @Ignore
    protected String name;

    @Ignore
    protected double price;

    @Ignore
    protected int imageResource;

    @Ignore
    protected String imageResourceString;

    @Ignore
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

    public String getImageResourceString() {
        return imageResourceString;
    }

    public boolean isBuyable() {
        return Game.getInstance().getCurrentMoney() >= price;
    }

    public void setImageResourceFromString() {
        imageResource = App.getContext().getResources().getIdentifier(imageResourceString, "drawable", App.getContext().getPackageName());
    }
}
