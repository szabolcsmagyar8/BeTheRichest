package com.betherichest.android.gameElements.upgrade;

import com.betherichest.android.R;
import com.betherichest.android.StatType;
import com.betherichest.android.mangers.Game;

public class GamblingUpgrade extends Upgrade {
    private int requiredGambling;

    public GamblingUpgrade(double price, double winAmountMultiplier, int requiredGambling, int color) {
        super(price, winAmountMultiplier, color);
        this.requiredGambling = requiredGambling;
        this.imageResource = R.drawable.clovermoney;
    }

    @Override
    public boolean isDisplayable() {
        return !purchased && Game.statisticsManager.getStatByType(StatType.TOTAL_GAMBLING).getValue() >= requiredGambling;
    }
}
