package com.betherichest.android.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.betherichest.android.GameState;
import com.betherichest.android.gameElements.GameStatistics;
import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.gameElements.achievement.Achievement;
import com.betherichest.android.gameElements.upgrade.Upgrade;

@Database(entities = {GameState.class, Investment.class, Upgrade.class, GameStatistics.class, Achievement.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract InvestmentDao investmentDao();

    public abstract UpgradeDao upgradeDao();

    public abstract GameStateDao gameStateDAO();

    public abstract GameStatisticsDao gameStatisticsDao();

    public abstract AchievementDao achievementDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'gamestate' ADD COLUMN 'sound_disabled' INTEGER DEFAULT 0 NOT NULL");
        }
    };

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "state_db")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }
}