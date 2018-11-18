package com.betherichest.android.gameElements.upgrade;

import com.betherichest.android.R;

public class GamblingUpgrade extends Upgrade {
    public GamblingUpgrade(double price, double winAmountMultiplier, int color) {
        super(price, winAmountMultiplier, color);
        this.imageResource = R.mipmap.clover;
    }

    @Override
    public boolean isDisplayable() {
        return !purchased;
    }
}
