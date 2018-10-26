package com.betherichest.android.gameElements;

import com.betherichest.android.R;
import com.betherichest.android.StatType;
import com.betherichest.android.mangers.StatisticsManager;

public class GlobalIncrementUpgrade extends Upgrade {
    public GlobalIncrementUpgrade(double price, double multiplier, int color) {
        super(price, multiplier, color);
        this.imageResource = R.drawable.globalincrement;
    }

    @Override
    public boolean isDisplayable() {
        return !purchased && StatisticsManager.getInstance().getStatByType(StatType.TOTAL_MONEY_COLLECTED).getValue() > price;
    }

}
