package com.betherichest.android.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.GameElements.Upgrade;
import com.betherichest.android.GameState;
import com.betherichest.android.GameElements.GameStatistics;

@Database(entities = {GameState.class, Investment.class, Upgrade.class, GameStatistics.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract InvestmentDao investmentDao();

    public abstract UpgradeDao upgradeDao();

    public abstract GameStateDao gameStateDAO();

    public abstract GameStatisticsDao gameStatisticsDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "state_db")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }
}