package com.betherichest.android;

/**
 * Created by I345179 on 2018. 06. 08..
 */

class TapUpgrade extends Upgrade {
    public TapUpgrade(String description, double price, int multiplierEffect, int imageResource, int color) {
        super(description, price, multiplierEffect, imageResource, color);
    }

    @Override
    public boolean isDisplayable() {
        return true;
    }
}
