package com.betherichest.android;

import android.content.Context;
import android.os.AsyncTask;
import android.support.multidex.MultiDexApplication;
import android.util.TypedValue;

import com.betherichest.android.connection.ActionType;
import com.betherichest.android.connection.ConnectionManager;
import com.betherichest.android.connection.RequestItem;
import com.betherichest.android.connection.RequestParam;
import com.betherichest.android.database.DatabaseManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Class for methods used a lot of times
public class App extends MultiDexApplication {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static NumberFormat NF = NumberFormat.getInstance(Locale.FRANCE);

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
        timeString += hours >= 1 ? (int) hours + "h " : "";
        timeString += minutes >= 1 ? (int) minutes + "m " : "";
        timeString += seconds > 0 ? (int) seconds + "s" : "";

        return timeString;
    }

    /**
     * @param convertThousand set true if you want to convert thousand too:  true: 1000 -> 1K; false: 1000 -> 1 000
     */
    public static String convertThousandsToSIUnit(double value, boolean convertThousand) {
        if (convertThousand && value >= 1000 && value < 10000) {
            return NF.format(value / 1000d) + "K";
        } else if (value >= 10000 && value < 1000000) {
            return NF.format(value / 1000d) + "K";
        } else if (value >= 1000000 && value < 1000000000) {
            return NF.format(value / 1000000d) + "M";
        } else if (value >= 1000000000 && value < 1000000000000d) {
            return NF.format(value / 1000000000d) + "B";
        } else if (value >= 1000000000000d) {
            return NF.format(value / 1000000000000d) + "T";
        }
        return NF.format(value);
    }

    /**
     * @param dp density pixel
     * @return pixel value
     */
    public static float getPixelFromDP(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    public static void createConnection(final String endPoint, final List<RequestParam> requestParams, final ActionType actionType) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (isOnline()) {
                    RequestItem requestItem = new RequestItem(endPoint, requestParams, actionType);
                    try {
                        new ConnectionManager(requestItem);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } else {
                    DatabaseManager.instance.saveRequestItemToDb(new RequestItem(endPoint, requestParams, actionType));
                }
            }
        });
    }

    public static void performQueuedRequests() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (RequestItem item : new ArrayList<>(ConnectionManager.requestItems)) {
                    createConnection(item.getEndPoint(), item.getRequestParams(), item.getActionType());
                }
            }
        });
    }
}
