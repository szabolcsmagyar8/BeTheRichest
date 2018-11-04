package com.betherichest.android.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.betherichest.android.GameState;
import com.betherichest.android.gameElements.Achievement;
import com.betherichest.android.gameElements.GameStatistics;
import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.gameElements.Upgrade;

@Database(entities = {GameState.class, Investment.class, Upgrade.class, GameStatistics.class, Achievement.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract InvestmentDao investmentDao();

    public abstract UpgradeDao upgradeDao();

    public abstract GameStateDao gameStateDAO();

    public abstract GameStatisticsDao gameStatisticsDao();

    public abstract AchievementDao achievementDao();

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