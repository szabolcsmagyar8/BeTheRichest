package com.betherichest.android;

public class GameStatistics {
    private int totalClick = 0;
    private int totalMoneyFromClick = 0;

    public int getTotalClick() {
        return totalClick;
    }

    public void setTotalClick(int totalClick) {
        this.totalClick = totalClick;
    }

    public int getTotalMoneyFromClick() {
        return totalMoneyFromClick;
    }

    public void setTotalMoneyFromClick(int totalMoneyFromClick) {
        this.totalMoneyFromClick = totalMoneyFromClick;
    }


    public void increaseTotalClicks(double moneyPerTap) {
        totalClick++;
        totalMoneyFromClick += moneyPerTap;
    }
}
