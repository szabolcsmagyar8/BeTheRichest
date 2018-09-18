package com.betherichest.android;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class GameStatistics {
    static int currentId = 0;

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "value")
    private double value;

    @Ignore
    private int imageResource;

    public GameStatistics(String name, double value, int imageResource) {
        this.id = currentId++;
        this.name = name;
        this.value = value;
        this.imageResource = imageResource;
    }

    public GameStatistics() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getValueAsString() {
        return String.valueOf(Math.round(value));
    }

    public int getImageResource() {
        return imageResource;
    }

    public void increaseValueByOne() {
        value++;
    }

    public void increaseValueByAmount(double amount) {
        value += amount;
    }
}

//public class GameStatistics {
//    static int currentId = 0;
//
//    private double totalClick = 0;
//
//    private double totalMoneyFromClick = 0;
//
//    private double totalMoneyCollected = 0;
//
//    private List<StatisticsItem> statsItems;
//
//    public void createStatItems() {
//        statsItems = new ArrayList<>();
//        Resources res = MainActivity.getContext().getResources();
//        statsItems.add(new StatisticsItem(res.getString(R.string.total_money_collected), totalMoneyCollected, R.drawable.ic_total_money));
//        statsItems.add(new StatisticsItem(res.getString(R.string.total_clicks), Math.round(totalClick), R.drawable.ic_click));
//        statsItems.add(new StatisticsItem(res.getString(R.string.total_money_from_clicks), Math.round(totalMoneyFromClick), R.drawable.ic_money));
//    }
//
//    public List<StatisticsItem> getStatsItems() {
//        return statsItems;
//    }
//
//    public void dollarClick(double moneyPerTap) {
//        totalClick++;
//        totalMoneyFromClick += moneyPerTap;
//        totalMoneyCollected += moneyPerTap;
//    }
//
//    public void addMoney(double money) {
//        totalMoneyCollected += money;
//    }
//
//    @Entity
//    public static class StatisticsItem {
//        @PrimaryKey
//        private int id;
//
//        @ColumnInfo(name = "name")
//        private String name;
//
//        @ColumnInfo(name = "value")
//        private double value;
//
//        @Ignore
//        private int imageResource;
//
//        public StatisticsItem(String name, double value, int imageResource) {
//            this.id = currentId++;
//            this.name = name;
//            this.value = value;
//            this.imageResource = imageResource;
//        }
//
//        public StatisticsItem() {
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public double getValue() {
//            return value;
//        }
//
//        public void setValue(double value) {
//            this.value = value;
//        }
//
//        public String getValueAsString() {
//            return String.valueOf(Math.round(value * 100.0) / 100.0);
//        }
//
//        public int getImageResource() {
//            return imageResource;
//        }
//    }
//}
