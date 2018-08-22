package com.betherichest.android.GameElements;

import com.betherichest.android.GameElements.Upgrade;
import com.betherichest.android.R;


public class TapUpgrade extends Upgrade {
    public TapUpgrade(double price, int multiplier, int color) {
        super(price, multiplier, color);
        this.imageResource = R.drawable.click;
    }

    @Override
    public boolean isDisplayable() {
        return !purchased;
    }
}
