package com.betherichest.android.gameElements;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.content.Context;

import com.betherichest.android.App;
import com.betherichest.android.R;

import java.util.Date;
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

        return String.valueOf(App.NF.format(Math.round(value)));
    }

    public long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public void increaseValueByOne() {
        value++;
    }

    public void increaseValueByAmount(double amount) {
        value += amount;
    }
}