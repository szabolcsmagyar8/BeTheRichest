package com.betherichest.android;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import java.io.IOException;

public class App extends MultiDexApplication {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getPlayingTimeString(double value) {
        double hours = value / 3600;
        double minutes = (value % 3600) / 60;
        double seconds = value % 60;

        String timeString = "";
        timeString += hours > 1 ? (int) hours + "h " : "";
        timeString += minutes >= 1 ? (int) minutes + "m " : "";
        timeString += seconds > 0 ? (int) seconds + "s" : "";

        return timeString;
    }
}
