package com.betherichest.android.gameElements;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.content.Context;

import com.betherichest.android.App;
import com.betherichest.android.R;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Entity
public class GameStatistics extends GameElement {
    @Ignore
    static int currentId = 0;

    @ColumnInfo(name = "value")
    private double value;

    public GameStatistics(String name, double value, int imageResource) {
        this.id = currentId++;
        this.name = name;
        this.value = value;
        this.imageResource = imageResource;
    }

    public GameStatistics(int id, double value) {
        this.id = id;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getValueAsString() {
        Context c = App.getContext();
        if (name.equals(c.getResources().getString(R.string.total_playing_time))) {
            return App.getPlayingTimeString(value);
        }
        if (name.equals(c.getString(R.string.first_dollar))) {
            long days = getDifferenceDays(new Date((long) value), new Date());
            return String.valueOf(days > 1 ? days + " days ago" : days + " day ago");
        }

        return String.valueOf(NumberFormat.getNumberInstance(Locale.FRANCE).format(Math.round(value)));
    }

    public long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

//    private String getPlayingTimeString() {
//        double hours = value / 3600;
//        double minutes = (value % 3600) / 60;
//        double seconds = value % 60;
//
//        String timeString = "";
//        timeString += hours > 1 ? (int) hours + "h " : "";
//        timeString += minutes > 1 ? (int) minutes + "m " : "";
//        timeString += seconds > 0 ? (int) seconds + "s" : "";
//
//        return timeString;
//    }

    public void increaseValueByOne() {
        value++;
    }

    public void increaseValueByAmount(double amount) {
        value += amount;
    }
}