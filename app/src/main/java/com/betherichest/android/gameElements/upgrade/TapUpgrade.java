package com.betherichest.android.gameElements.upgrade;

import com.betherichest.android.R;
import com.betherichest.android.StatType;
import com.betherichest.android.mangers.Game;


public class TapUpgrade extends Upgrade {
    private double clickRequired;

    public TapUpgrade(double price, double multiplier, double clickRequired, int color) {
        super(price, multiplier, color);
        this.clickRequired = clickRequired;
        this.imageResource = R.drawable.moneyclick;
    }

    @Override
    public boolean isDisplayable() {
        return !purchased && clickRequired < Game.statisticsManager.getStatByType(StatType.TOTAL_CLICKS).getValue();
    }
}
